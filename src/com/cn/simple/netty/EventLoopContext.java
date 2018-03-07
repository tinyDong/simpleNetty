package com.cn.simple.netty;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLoopContext {

    private AtomicInteger bossIndex=new AtomicInteger();
    private BossEventLoop[] bossEventLoops;

    private AtomicInteger workerIndex=new AtomicInteger();
    private WorkerEventLoop[] workerEventLoops;

    public EventLoopContext(Executor boss,Executor worker) {
    }
}
