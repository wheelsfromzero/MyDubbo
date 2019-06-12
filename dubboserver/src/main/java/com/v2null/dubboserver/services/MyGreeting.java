package com.v2null.dubboserver.services;

import com.v2null.dubbo.server.DubboService;
import com.v2null.greetinterface.Greeting;

@DubboService(interfaceName = "Greeting")
public class MyGreeting implements Greeting {
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
