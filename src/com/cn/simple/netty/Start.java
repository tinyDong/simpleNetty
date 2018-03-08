package com.cn.simple.netty;

import com.cn.simple.netty.EventLoop.EventLoopProvider;

import java.util.concurrent.Executors;

public class Start {
    public static void main(String[] args) {
        EventLoopProvider eventLoopProvider=new EventLoopProvider(Executors.newFixedThreadPool(1),Executors.newFixedThreadPool(1));
        ServerBootStrap bootStrap=new ServerBootStrap(eventLoopProvider);

    }
}
