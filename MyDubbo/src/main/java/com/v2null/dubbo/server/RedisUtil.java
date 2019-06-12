package com.v2null.dubbo.server;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static RedisUtil instance = new RedisUtil();

    private Jedis jedis;

    public static RedisUtil getInstance()
    {
        return instance;
    }

    private RedisUtil(){

    }

    public Jedis getJedis()

    {
        this.jedis = new Jedis("127.0.0.1", 6379);
        return this.jedis;
    }




}
