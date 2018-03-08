package com.cn.simple.netty;

import com.cn.simple.netty.EventLoop.EventLoopProvider;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Start {
    public static void main(String[] args) {
        EventLoopProvider eventLoopProvider=new EventLoopProvider(Executors.newFixedThreadPool(1),Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
        ServerBootStrap bootStrap=new ServerBootStrap(eventLoopProvider);
        bootStrap.bind(new InetSocketAddress(3333));
        System.out.println("start");

    }
}
