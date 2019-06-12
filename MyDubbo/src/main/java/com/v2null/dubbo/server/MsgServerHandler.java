package com.v2null.dubbo.server;

import com.v2null.dubbo.common.RpcRequest;
import com.v2null.dubbo.common.RpcResponse;
import io.netty.buffer.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

public class MsgServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;
        Object serviceImp = MyDubboServer.getInstance().getServiceInstance(rpcRequest.getClassName());
        Method method;
        if (rpcRequest.getArgs() == null) {
            method = serviceImp.getClass().getMethod(rpcRequest.getMethodName());
        } else {
            Class[] types = new Class[rpcRequest.getArgs().length];
            for (int i = 0; i < rpcRequest.getArgs().length; i++) {
                types[i] = rpcRequest.getArgs()[i].getClass();
            }
            method = serviceImp.getClass().getMethod(rpcRequest.getMethodName(), types);
        }
        Object obj = method.invoke(serviceImp, rpcRequest.getArgs());
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setUuid(rpcRequest.getUuid());
        rpcResponse.setObject(obj);
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeZero(4);
        ObjectOutputStream stream = new ObjectOutputStream(new ByteBufOutputStream(byteBuf));
        stream.writeObject(rpcResponse);
        byteBuf.setInt(0, byteBuf.readableBytes() - 4);
        ChannelFuture cf = ctx.write(byteBuf);
        ctx.flush();
        if (!cf.isSuccess()) {
            System.out.println("Send failed: " + cf.cause());
        }
    }

}
