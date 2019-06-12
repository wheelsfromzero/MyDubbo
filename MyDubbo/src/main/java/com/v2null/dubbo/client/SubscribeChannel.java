package com.v2null.dubbo.client;

import redis.clients.jedis.JedisPool;

public class SubscribeChannel {
    public static void subscribeChannle(String channel)
    {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

        MySubThread subThread = new MySubThread(jedisPool, channel);
        subThread.start();
    }
}
