package com.cn.simple.netty;

import java.util.concurrent.Executor;

public class WorkerEventLoop extends EventLoop{
    public WorkerEventLoop(Executor executor, String threadName) {
        super(executor, threadName);
    }
}
