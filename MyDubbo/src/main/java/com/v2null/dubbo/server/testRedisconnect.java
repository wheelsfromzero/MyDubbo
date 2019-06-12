package com.v2null.dubbo.server;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class testRedisconnect {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("Greeting", "127.0.0.1:8088");
        System.out.println(jedis.get("Greeting"));
    }
}
