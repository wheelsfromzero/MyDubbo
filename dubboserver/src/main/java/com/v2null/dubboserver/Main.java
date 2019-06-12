package com.v2null.dubboserver;

import com.v2null.dubbo.server.MyDubboServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        MyDubboServer.getInstance()
                .addPackageName("com.v2null.dubboserver.services")
                .startServer(8088);
    }
}
