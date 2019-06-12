package com.v2null.dubbo.client;

import com.v2null.dubbo.common.RpcRequest;
import com.v2null.dubbo.common.RpcResponse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class DynamicProxy implements InvocationHandler {
    private MyNettyClient client;

    public DynamicProxy(){

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        String address = "";
        int port = -1;
        ExistsChannels existsChannels = ExistsChannels.getInstance();
        String channel = method.getDeclaringClass().getSimpleName();

        if(!existsChannels.ifChannelExists(channel)) {
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            String addAndPort = jedis.get(channel);
            int index = addAndPort.indexOf(":");
            address = addAndPort.substring(0, index);
            port = Integer.parseInt(addAndPort.substring(index + 1, addAndPort.length()));
            SubscribeChannel.subscribeChannle(channel);

        }else
        {
            String addAndPort = existsChannels.getMsg(channel);
            int index = addAndPort.indexOf(":");
            address = addAndPort.substring(0, index);
            port = Integer.parseInt(addAndPort.substring(index + 1, addAndPort.length()));
        }

        if(port != -1)
        {
            client = new MyNettyClient(address, port);
            RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getSimpleName(), method.getName(), args);
            client.sendMessaqe(rpcRequest, rpcRequest.getUuid()).sync();
            RpcResponse rpcResponse = (RpcResponse)client.getMessage();
            client.close();
            return rpcResponse.getObject();
        }
        return null;
    }

}
