package com.tjr.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest1 {
    private int j;

    public static void main(String args[]) {
        int iii=10/8;
        System.out.println(iii);
//        ThreadTest1 tt = new ThreadTest1();
//        Inc inc = tt.new Inc();
//        Dec dec = tt.new Dec();
//        for (int i = 0; i < 2; i++) {
//            Thread t = new Thread(inc);
//            t.start();
//            t = new Thread(dec);
//            t.start();
//        }
    }

    private synchronized void inc() {
        j++;
        System.out.println(Thread.currentThread().getName() + "-inc:" + j);
    }

    private synchronized void dec() {
        j--;
        System.out.println(Thread.currentThread().getName() + "-dec:" + j);
    }

    class Inc implements Runnable {
        public void run() {
            for (int i = 0; i < 100; i++) {
                inc();
            }
        }
    }

    class Dec implements Runnable {
        public void run() {
            for (int i = 0; i < 100; i++) {
                dec();
            }
        }
    }
    public static void testlock() {
        Lock lock = new ReentrantLock();

       
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                lock.lock();
                try {
                    Thread.sleep(1000);
                    System.out.println("goon");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        });

        t.start();
        System.out.println("start");
        lock.lock();
        System.out.println("over");
        lock.unlock();
    }



}