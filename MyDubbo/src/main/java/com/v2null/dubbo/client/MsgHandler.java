package com.v2null.dubbo.client;

import com.v2null.dubbo.common.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MsgHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext context;
    private Object rcv;
    private Map<UUID, ChannelPromise> idPromiseMap;
    public Object getRcv() {
        return rcv;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
        idPromiseMap = new HashMap<>();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        rcv = msg;
        idPromiseMap.get(((RpcResponse) msg).getUuid()).setSuccess();
        ctx.close().sync();

    }

    public ChannelPromise sendMsg(Object msg, UUID id) throws IOException {
        if (context == null) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ByteBuf byteBuf = context.alloc().buffer();
        byteBuf.writeZero(4);
        ObjectOutputStream stream = new ObjectOutputStream(new ByteBufOutputStream(byteBuf));
        stream.writeObject(msg);
        byteBuf.setInt(0, byteBuf.readableBytes() - 4);

        ChannelPromise promise = context.channel().newPromise();
        idPromiseMap.put(id, promise);
        context.writeAndFlush(byteBuf);
        return promise;
    }


}
