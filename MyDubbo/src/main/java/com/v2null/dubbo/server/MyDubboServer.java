package com.v2null.dubbo.server;

import cn.fh.pkgscanner.PkgScanner;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;

public class MyDubboServer {

    private static MyDubboServer instance = new MyDubboServer();

    private HashMap<String, Object> serviceMap;

    public static MyDubboServer getInstance() {
        return instance;
    }


    private MyDubboServer() {
        this.serviceMap = new HashMap<>();
    }

    public void startServer(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //.childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MsgServerDecoder(), new MsgServerHandler());
                        }
                    });
            //.option(ChannelOption.SO_BACKLOG, 128);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public MyDubboServer addPackageName(String pkgName) {
        this.scanPackage(pkgName);
        return this;
    }

    public void registerService(String name, Object impl) {
        if (impl != null) {
            this.serviceMap.put(name, impl);
            Jedis jedis = RedisUtil.getInstance().getJedis();
            jedis.set(name, "127.0.0.1:8088");
            System.out.println(jedis.get(name));
        }
    }

    public Object getServiceInstance(String serviceName) {
        return this.serviceMap.get(serviceName);
    }

    private void scanPackage(String pkgName) {
        PkgScanner scanner = new PkgScanner(pkgName);
        List<String> classNames = null;
        try {
            classNames = scanner.scan();
            for(String className: classNames)
            {
                Class impClass = Class.forName(className);
                for (Annotation anno : impClass.getAnnotations()) {
                    if (anno.annotationType().equals(DubboService.class)) {
                        DubboService service = (DubboService)anno;
                        this.registerService(service.interfaceName(), impClass.newInstance());
                    }
                }

            }

        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
