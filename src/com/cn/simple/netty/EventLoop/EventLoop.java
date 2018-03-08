package com.cn.simple.netty.EventLoop;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class EventLoop implements Runnable{

    /**
     * 线程池
     */
    private final Executor executor;

    /**
     * 选择器
     */
    protected Selector selector;

    /**
     * 选择器wakenUp状态标记
     */
    protected final AtomicBoolean wakenUp = new AtomicBoolean();

    /**
     * 任务队列
     */
    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * eventLoop管理者
     */
    private EventLoopProvider eventLoopProvider;

    public EventLoop(Executor executor,String threadName,EventLoopProvider eventLoopProvider) {
        this.threadName=threadName;
        this.executor = executor;
        this.eventLoopProvider=eventLoopProvider;
        openSelector();
    }

    private void openSelector() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create a selector.");
        }
        executor.execute(this);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.threadName);
        while (!Thread.currentThread().isInterrupted()){
            wakenUp.set(false);
            try {
                selector.select();

                processTaskQueue();

                processSelector(selector);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected final void executeTask(Runnable task){
        taskQueue.add(task);

        Selector selector=this.selector;

        if (selector==null){
            taskQueue.remove(task);
            return;
        }

        if (wakenUp.compareAndSet(false,true)){
            selector.wakeup();
        }
    }



    private void processTaskQueue() {
        while (!Thread.currentThread().isInterrupted()){
            Runnable task=taskQueue.poll();
            if (task==null){
                break;
            }
            task.run();
        }
    }

    /**Boss 和 Worker需要不同的处理逻辑
     * @param selector
     * @throws IOException
     */
    protected abstract void processSelector(Selector selector) throws IOException;



    /**
     * 获取EventLoop提供者
     * @return
     */
    public EventLoopProvider getEventLoopProvider() {
        return eventLoopProvider;
    }
}
