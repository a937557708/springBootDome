package com.tjr.sync;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 1、什么是 CB???
 *
 * CyclicBarrier 字面意思是可循环（Cyclic）的屏障（Barrier）。它主要做的事情是，让一组线程达到一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活，线程进入屏障通过 CyclicBarrier 的 await 方法。
 *
 * 2、CB 的基本语法
 *
 * ① 初始化
 *
 * public CyclicBarrier(int parties)
 *
 * ② getNumberWaiting()
 *
 * public int getNumberWaiting()
 *
 * 返回当前在屏障处等待的参与者数目。
 *
 * ③ await
 *
 * 在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
 */
public class CyclicBarrierDome {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("完成");
        });
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + ":开始_"+ii);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Tread-" + i).start();
        }
    }
}
