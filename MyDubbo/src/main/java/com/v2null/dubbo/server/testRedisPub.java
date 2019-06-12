package com.v2null.dubbo.server;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testRedisPub {
    public static void main(String[] args) throws InterruptedException {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Publisher publisher = new Publisher(jedisPool);
        publisher.start();
        Jedis jedis = jedisPool.getResource();   //连接池中取出一个连接
        while (true) {
            Thread.sleep(50000);
            String line = "127.0.0.1" + ":" + "8088";
            jedis.publish("Greeting", line);   //从 mychannel 的频道上推送消息
        }

    }
}
