package com.v2null.dubbo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.ObjectInputStream;
import java.util.List;

public class MsgDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out)
            throws Exception {
        int len = byteBuf.readInt();
        ByteBufInputStream byteBufInputStream = new ByteBufInputStream(byteBuf.readBytes(len));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteBufInputStream);
        out.add(objectInputStream.readObject());
    }
}