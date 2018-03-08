package com.cn.simple.netty.EventLoop;

import com.cn.simple.netty.EventLoop.EventLoop;

import java.io.IOException;
import java.nio.channels.*;
import java.util.concurrent.Executor;

public class WorkerEventLoop extends EventLoop implements Worker{


    public WorkerEventLoop(Executor executor, String threadName, EventLoopProvider eventLoopProvider) {
        super(executor, threadName, eventLoopProvider);
    }

    @Override
    protected void processSelector(Selector selector) throws IOException {

    }

    @Override
    public void registServerChannel(final SocketChannel socketChannel) {
        final Selector selector=this.selector;
        executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
