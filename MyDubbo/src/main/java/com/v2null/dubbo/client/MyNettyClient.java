package com.v2null.dubbo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.UUID;

public class MyNettyClient {

    private MsgHandler handler = new MsgHandler();
    private ChannelFuture cf;
    private String ipAddress;

    public MyNettyClient(String ipAddress, int port) throws Exception
    {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                //.option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ClientInitializer(handler));
        cf = b.connect(ipAddress, port).sync();
    }


    public ChannelPromise sendMessaqe(Object msg, UUID id) throws IOException {
        return handler.sendMsg(msg, id);
    }


    public Object getMessage() {

        return handler.getRcv();
    }

    public void close() throws InterruptedException {
        cf.channel().close().sync();
    }

}
