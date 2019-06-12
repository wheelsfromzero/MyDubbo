package com.v2null.dubbo.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MyDubboClient {
    public static Object createInstance(Class<?> interfaceClass) throws Exception {
        InvocationHandler handler = new DynamicProxy();

        return  Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                new Class[]{interfaceClass}, handler);
    }

}
