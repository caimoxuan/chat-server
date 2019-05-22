package com.cmx.chatserver.chat.test.thread;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author cmx
 * @date 2019/5/21
 */
public class MyFixedThreadPool {

    private LinkedBlockingQueue<Runnable> blockingQueue;


    private class Worker implements Runnable {

        private MyFixedThreadPool myFixedThreadPool;

        Worker(MyFixedThreadPool myFixedThreadPool) {
            this.myFixedThreadPool = myFixedThreadPool;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable take = this.myFixedThreadPool.blockingQueue.take();
                    if (take != null) {
                        take.run();
                        System.out.println("一个线程启动" + Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    MyFixedThreadPool(int coreSize, int waitSize) {
        this.blockingQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < coreSize; i++) {
            Worker worker = new Worker(this);
            new Thread(worker).start();
        }
    }

    public boolean submit(Runnable runnable) {
        return this.blockingQueue.offer(runnable);
    }

    public void execute(Runnable runnable) throws InterruptedException {
        this.blockingQueue.put(runnable);
    }

    public static void main(String[] args) {
        MyFixedThreadPool myFixedThreadPool = new MyFixedThreadPool(3, 6);
        for (int i = 0; i < 10; i++) {
            myFixedThreadPool.submit(() -> {
                try {
                    System.out.println("runnable in");
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
