package com.v2null.dubbo.client;

import redis.clients.jedis.JedisPool;

public class testRedisSub {
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

        MySubThread subThread = new MySubThread(jedisPool, "httChannel1");
        subThread.start();
        MySubThread subThread1 = new MySubThread(jedisPool, "httChannel2");
        subThread1.start();
    }
}
