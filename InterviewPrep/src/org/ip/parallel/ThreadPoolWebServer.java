package org.ip.parallel;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// EPI 2018: 19.4
public class ThreadPoolWebServer {
	public static void main(String[] s) {
		ThreadPoolWebServer server = new ThreadPoolWebServer();
		server.run();
	}
	ExecutorService service = Executors.newFixedThreadPool(2);
	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(80);
		} catch (IOException e2) {
			e2.printStackTrace();
			return;
		}
		while (!Thread.interrupted()) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				service.execute(new Runnable() {
					@Override
					public void run() {
						//process
						try {
							InputStream is = socket.getInputStream();
							is.transferTo(System.out);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}});
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		service.shutdownNow();
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
