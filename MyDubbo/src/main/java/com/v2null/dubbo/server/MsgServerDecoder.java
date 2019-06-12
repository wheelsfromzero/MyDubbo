package com.v2null.dubbo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.ObjectInputStream;
import java.util.List;

public class MsgServerDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out)
            throws Exception {
        int len = byteBuf.readInt();
        System.out.println(len);
        ByteBufInputStream byteBufInputStream = new ByteBufInputStream(byteBuf.readBytes(len));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteBufInputStream);
        out.add(objectInputStream.readObject());
    }
}
