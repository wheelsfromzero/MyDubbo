package com.v2null.dubbo.redistest;

import redis.clients.jedis.Jedis;

public class redistest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println(jedis.ping());

    }

}
