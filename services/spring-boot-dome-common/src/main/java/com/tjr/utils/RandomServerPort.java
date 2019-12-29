package com.tjr.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang3.RandomUtils;

public class RandomServerPort {
	private int serverPort;

	private final int start = 0;
	private final int end = 65535;

	public int nextValue(int start) {
		return nextValue(start, end);
	}
	

	public int nextValue(int start, int end) {
		if(start>end) {
			return 0;
		}
		start = start < this.start ? this.start : start;
		end = end > this.end ? this.end : end;

		if (serverPort == 0) {
			synchronized (this) {
				if (serverPort == 0) {
					serverPort = RandomUtils.nextInt(start, end);
					if (isLocalPortUsing(serverPort)) {
						serverPort = 0;
					}
				}
			}
		}
		return serverPort;
	}

	/**
	 * 测试本机端口是否被使用
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isLocalPortUsing(int port) {
		boolean flag = true;
		try {
			// 如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
			flag = isPortUsing("127.0.0.1", port);
		} catch (Exception e) {
		}
		return flag;
	}

	/***
	 * 测试主机Host的port端口是否被使用
	 * 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	public static boolean isPortUsing(String host, int port) throws UnknownHostException {
		boolean flag = false;
		InetAddress Address = InetAddress.getByName(host);
		try {
			Socket socket = new Socket(Address, port); // 建立一个Socket连接
			flag = true;
		} catch (IOException e) {

		}
		return flag;
	}
}
