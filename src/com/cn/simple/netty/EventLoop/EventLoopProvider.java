package com.cn.simple.netty.EventLoop;

import com.cn.simple.netty.EventLoop.BossEventLoop;
import com.cn.simple.netty.EventLoop.WorkerEventLoop;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLoopProvider {


    private AtomicInteger bossIndex=new AtomicInteger();
    private BossEventLoop[] bosses;

    private AtomicInteger workerIndex=new AtomicInteger();
    private WorkerEventLoop[] workers;

    public EventLoopProvider(Executor boss, Executor worker) {
        initBoss(boss,1);
        initWorker(worker,Runtime.getRuntime().availableProcessors() * 2);
    }

    private void initWorker(Executor worker, int count) {
        workers=new WorkerEventLoop[count];
        for (int i=0;i<count;i++){
            workers[i]=new WorkerEventLoop(worker,"worker"+i,this);
        }
    }

    private void initBoss(Executor boss, int count) {
        bosses=new BossEventLoop[count];
        for (int i=0;i<count;i++){
            bosses[i]=new BossEventLoop(boss,"boss"+i,this);
        }
    }


    /**获取一个bossEventLoop
     * @return
     */
    public BossEventLoop nextBoss(){
        return bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
    }
    /**获取一个workerEventLoop
     * @return
     */
    public WorkerEventLoop nextWorker(){
        return workers[Math.abs(workerIndex.getAndIncrement() % workers.length)];
    }
}
