package com.hmemcpy.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private int port = 8821;
	private ServerSocket serverSocket;
	private ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 10;// 单个CPU线程池大小

	
	static int sum = 0;
	
	public Server() throws IOException {
		try{
			serverSocket = new ServerSocket(port);
			// Runtime的availableProcessor()方法返回当前系统的CPU数目.
			executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors() * POOL_SIZE);
			System.out.println("服务器启动");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void service() {
		while (true) {
			Socket socket = null;
			try {
				// 接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	class Handler implements Runnable {
		Socket socket = null;

		public Handler(Socket socket) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
		}

		public void run() {
			// TODO Auto-generated method stub

			
			System.out.println("Thread " + sum++);
			
			try {
				File file = new File("list.xml");
				InputStream in = new FileInputStream(file);

				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

				StringBuilder sb = new StringBuilder();

				String temp = "";
				while ((temp = reader.readLine()) != null) {
					sb.append(temp);
				}

				OutputStream out = socket.getOutputStream();
				out.write(sb.toString().getBytes());
				out.close();

				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}

