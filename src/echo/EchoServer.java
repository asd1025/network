package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int  PORT=7000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {// 서버 소켓에 관한 try catch
			// 1. 서버소켓 생성
		serverSocket = new ServerSocket();
		
		 

		// 2. 주소 바인딩 (binding)
		serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
		log("server status...[port:"+PORT+"]");
		while(true) {
		// 3. accept : 클라이언트의 연결요청을 기다린다.
		 Socket socket = serverSocket.accept(); // blocking
		Thread thread = new EchoServerReceiveThread(socket);
		thread.start();
		
		}
		// (클라이언트 누가 접속했는지 남김) 192.168.1.36
		 
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	}
	public static void log(String log) {
		System.out.println("[Server#"+ Thread.currentThread().getId()+" ] "+log);
	}
	
}
