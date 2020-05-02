package com.tjr.sync;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 是一个计数信号量，必须由获取它的线程释放。作用是控制并发的数量。
 *
 * 就像我们去火车站的洗手间，如果只有 3 个坑，多出来的人就必须在门外面等着，干着急。
 */
public class SemaphoreDome {
    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(3);
        for (int i=0;i<10;i++){
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+":开始");
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName()+":结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                }
            });
            thread.setName("thread_"+i);
            thread.start();
        }
    }
}
