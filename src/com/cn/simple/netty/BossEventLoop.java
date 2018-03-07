package com.cn.simple.netty;

import java.util.concurrent.Executor;

public class BossEventLoop extends EventLoop{
    public BossEventLoop(Executor executor, String threadName) {
        super(executor, threadName);
    }
}
