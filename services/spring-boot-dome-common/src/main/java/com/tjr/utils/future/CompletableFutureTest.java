package com.tjr.utils.future;


import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.*;


public class CompletableFutureTest {

    private ExecutorService executor
            = Executors.newSingleThreadExecutor();


    /**
     * 创建一个简单的没有返回值的CompleteFuture
     * runAsync 没有返回值
     * 耗时 3038
     */
    @Test
    public void fun01() throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("----------run方法开始执行,休眠{}秒-------\ta:{}\tb:{}");
                Thread.sleep(2 * 1000);

                System.out.println("----------run方法结束执行--------\tresult:{}");

            } catch (Exception e) {
                e.printStackTrace();

            }
        }, executor);
        Thread.sleep(3000);

    }

    /**
     * 创建一个简单的没有返回值的CompleteFuture
     * supplyAsync 有返回值,类似Future
     * CompletableFuture使用supplyAsync(),会异步调用方法,调用get()方法,会在获取到future返回值后,再执行get()以后的程序
     * 耗时 3040
     */
    @Test
    public void fun02() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("----------run方法开始执行,休眠{}秒-------\ta:{}\tb:{}");
                Thread.sleep(2 * 1000);

                System.out.println("----------run方法结束执行--------\tresult:{}");
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 2;
            }
        }, executor);
        System.out.println("----get阻塞开始----");
        Integer i = f.get();
        System.out.println("----get阻塞结束----");
    }

    @Test
    public void test2() {
        //创建一个已经有任务结果的CompletableFuture
        CompletableFuture<String> f1 = CompletableFuture.completedFuture("return value");
        //异步处理任务,有返回值
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::get);
        //异步处理任务,没有返回值
        CompletableFuture<Void> f3 = CompletableFuture.runAsync(System.out::println);
        //需要等待所有的异步任务都执行完毕,才会返回一个新的CompletableFuture
//        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
        //任意一个异步任务执行完毕,就会返回一个新的CompletableFuture
        CompletableFuture<Void> any = CompletableFuture.allOf(f1, f2, f3);
        Object result = any.join();
        System.out.println("result = " + result);//result = return value
    }

    public String get() {
        delay();
        return "异步任务结果";
    }

    public void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {


    }
}
