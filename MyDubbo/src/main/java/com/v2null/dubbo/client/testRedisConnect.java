package com.v2null.dubbo.client;

import redis.clients.jedis.Jedis;

public class testRedisConnect {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.get("Greeting"));
    }
}
