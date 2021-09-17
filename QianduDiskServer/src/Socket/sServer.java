package Socket;

import java.io.*;
import java.net.*;
import java.util.*;

import Database.db;

public class sServer {

	private static ServerSocket serverSocket;
	private static Socket socket;

//	初始化服务器
	public sServer(int port) {

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务器加载成功");
			db.init();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deal() {
//		循环监听，可以同时处理多个客户端的socket，通过多线程实现
		while (true) {
			try {
//				每收到一个新的socket就分给一个新的线程处理
//				所以这里写了一个新的类ServerThread继承了Thread，专门用来处理客户端请求
				socket = serverSocket.accept();
				ServerThread thread = new ServerThread(socket);
				thread.run();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


//测试
	public static void main(String[] args) {

		sServer Server = new sServer(43211);
		Server.deal();
	}

}
