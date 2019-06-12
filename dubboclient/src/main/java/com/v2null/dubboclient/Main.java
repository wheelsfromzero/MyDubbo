package com.v2null.dubboclient;

import com.v2null.dubbo.client.MyDubboClient;
import com.v2null.greetinterface.Greeting;

public class Main {
    public static void main(String[] args) throws Exception {
        Greeting greeting = (Greeting) MyDubboClient.createInstance(Greeting.class);
        System.out.println(greeting.sayHello("Zachary"));
        System.out.println(greeting.sayHello("Zachary123"));
    }
}
