package com.tjr.sync;

import java.util.concurrent.CountDownLatch;

/**
 * CDL 能够使 1 个线程在等待另外 n 个线程完成各自工作之后，再继续执行。
 * <p>
 * 还是举火车站上厕所的例子， CDL 规则就是等待 3 个人都上完厕所，清洁工才进厕所打扫卫生
 */
public class CountDownLatchDome {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":开始");
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + ":结束");
                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("thread_" + i);
            thread.start();
        }

        try {

            System.out.println("等待10个人:开始");
            countDownLatch.await();
            System.out.println("等待10个人:全部结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
