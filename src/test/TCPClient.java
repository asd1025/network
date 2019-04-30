package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
	private static final String SERVER_IP="192.168.1.36";
	private static final int SERVER_PORT=6000;
public static void main(String[] args) {
	Socket socket =null;
	try {
		//1. 소켓 생성
		  socket= new Socket();
			// 2-1. 소켓 버퍼 사이즈 확인
			int resceiveBufferSiz=socket.getReceiveBufferSize();
			int sendBufferSize=socket.getSendBufferSize();
			System.out.println(resceiveBufferSiz+" : "+sendBufferSize);
			
			//2-2. 소켓 버퍼 사이즈 변경
			socket.setReceiveBufferSize(1024*10);
			socket.setSendBufferSize(1024*10);
			 resceiveBufferSiz=socket.getReceiveBufferSize();
			 sendBufferSize=socket.getSendBufferSize();
				System.out.println(resceiveBufferSiz+" : "+sendBufferSize);
				
				//1-3 SO_NODELAY (Nagel Algorithm off)
			socket.setTcpNoDelay(true);
			
			//1-4. SO_TIMEOUT 
			socket.setSoTimeout(1000);
				
				
				
				
				
				
				
				
				
				
		//2. 서버 연결
		socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		// cmd 가서 d: -> cafe24 eclipse-workspace neetwork bin java test.TCPServer
		
		//3. IOStream 받아오기
		InputStream is= socket.getInputStream();
		OutputStream os=socket.getOutputStream();
		
		//4. 쓰기 
		String data = "Hello World\n";
		os.write(data.getBytes("utf-8"));
		
		//5. 읽기
		byte[] buffer =new byte[256];
		int readByteCount = is.read(buffer); //blocking
		if(readByteCount==-1) {
			System.out.println("[client] closed by ");
		}
		
		 data=new String(buffer,0,readByteCount,"utf-8");
		System.out.println("[client] received: "+data);
		
		
		
		
	} catch (SocketTimeoutException e) {
		System.out.println("[clinet] time out");
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
			 try {
				 if(socket!=null && !socket.isClosed() ) {
				socket.close();
				 }
			} catch (IOException e) {
				e.printStackTrace();
		 }
	}
	
}
}
