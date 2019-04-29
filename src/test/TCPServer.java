package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {// 서버 소켓에 관한 try catch
				// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 2. 주소 바인딩 (binding)
			// : Socket에 SocketAddress(IPAderress + Port)를 바인딩한다
			InetAddress indetAddress = InetAddress.getLocalHost();
			// String localHost=indetAddress.getHostAddress();
			// serverSocket.bind(new InetSocketAddress(localhost, 5000));
			// serverSocket.bind(new InetSocketAddress(indetAddress, 5000));
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 6000));

			// 3. accept
			// : 클라이언트의 연결요청을 기다린다.
			Socket socket = serverSocket.accept(); // blocking

			// (클라이언트 누가 접속했는지 남김) 192.168.1.36
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			System.out.println("[server] Conected by clinet[" + remoteHostAddress + ":" + remotePort + "]");

			try {// 데이터 소켓에 관한 try catch
				// 4. IOStream 받아보기 (자바는 모든 스트림으로 추상화 시킴)
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount=  is.read(buffer);
					if(readByteCount==-1) {
						// 클라이언트 정상종료한 경우
						// close() 메소드 호출
						System.out.println("[server] closed by client");
						break;
					}
					
					String data=new String(buffer,0,readByteCount,"utf-8");
					System.out.println("[server] recevied : "+data);
				
					//6. 데이터쓰기
					os.write(data.getBytes("utf-8"));
				
				}

			}catch (SocketException e) {
				System.out.println("[server] Sudden Closed by Client : ");
			}  catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null && !socket.isClosed())
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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

}
