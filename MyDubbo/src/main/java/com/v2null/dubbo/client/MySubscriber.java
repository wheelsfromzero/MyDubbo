package com.v2null.dubbo.client;

import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;

public class MySubscriber extends JedisPubSub {


    public MySubscriber() {

    }

    @Override
    public void onMessage(String channel, String message) {
        ExistsChannels channels = ExistsChannels.getInstance();
        channels.setChannel(channel, message);
        System.out.println("client receive the message ");
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("client subscribe " + channel + " " + subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("client unsubscribe " + channel + " " + subscribedChannels);
    }
}
