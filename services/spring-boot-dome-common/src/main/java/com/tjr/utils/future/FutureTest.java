package com.tjr.utils.future;

import java.util.concurrent.*;

public class FutureTest {
    private ExecutorService executor
            = Executors.newSingleThreadExecutor();

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            System.out.println("Calculating..."+ input);
            Thread.sleep(1000);
            return input * input;
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTest futureTest=new FutureTest();
        Future<Integer> futureOne = futureTest.calculate(20);
        while(!futureOne.isDone()) {  //判断是否 完成
            System.out.println("Calculating...");
            Thread.sleep(300);
            boolean canceled = futureOne.cancel(true); // 取消任务
        }
        boolean canceled = futureOne.isCancelled();
        if(!canceled){
//            Integer result = futureOne.get();
            Integer result = futureOne.get(100,TimeUnit.MILLISECONDS);// 超时等待值
            System.out.println(result);
        }


    }
}
