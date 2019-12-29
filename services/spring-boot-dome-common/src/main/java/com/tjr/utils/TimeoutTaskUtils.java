package com.tjr.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

public class TimeoutTaskUtils {
	/**
	 * 执行一个有时间限制的任务
	 * 
	 * @param task    待执行的任务
	 * @param seconds 超时时间(单位: 秒)
	 * @return
	 */
	public static Object execute(Event event, int seconds) {
		Object result = new Object();
		ExecutorService threadPool = Executors.newCachedThreadPool();

		try {
			Future<Object> future = threadPool.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return event.processEvent();
				}
			});
			result = future.get(seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			result = Boolean.FALSE;
			e.printStackTrace();
		} finally {
			threadPool.shutdownNow();
		}

		return result;
	}

	public static void main(String[] args) {
		Event event = () -> {
			JSONObject obj=new JSONObject();
			
			return obj;
		};
		execute(event, 1);
	}
}
