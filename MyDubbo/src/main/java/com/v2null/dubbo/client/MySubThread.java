package com.v2null.dubbo.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MySubThread extends Thread {
    private JedisPool jedisPool;
    private MySubscriber subscriber = new MySubscriber();

    private String channel;

    public MySubThread(JedisPool jedisPool, String channel) {
        this.jedisPool = jedisPool;
        this.channel = channel;
    }

    @Override
    public void run() {
        Jedis jedis = jedisPool.getResource();
        jedis.subscribe(subscriber, channel);
    }
}
