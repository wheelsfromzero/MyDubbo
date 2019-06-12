package com.v2null.dubbo.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private MsgHandler handler;

    public ClientInitializer(MsgHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //ch.pipeline().addLast(new WriteTimeoutHandler(5));
        ch.pipeline().addLast(new MsgDecoder(), handler);
        //ch.pipeline().addLast(new ReadTimeoutHandler(5));

    }

}
