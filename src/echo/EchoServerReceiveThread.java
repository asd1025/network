package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
private Socket socket;
public EchoServerReceiveThread(Socket socket) {this.socket=socket;}
@Override
	public void run() {
	
	// (클라이언트 누가 접속했는지 남김) 192.168.1.36
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			EchoServer.log(" Conected by clinet[" + remoteHostAddress + ":" + remotePort + "]");

			try {// 데이터 소켓에 관한 try catch
				// 4. IOStream 받아보기 (자바는 모든 스트림으로 추상화 시킴)
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
				PrintWriter pr=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
				
				while(true) {
					//5. 데이터 읽기
					String data=br.readLine();
					if(data==null) {
						EchoServer.log("closed by client");
						break;
					}
					EchoServer.log("received: "+data);
					
					//6. 데이터 쓰기
					pr.println(data);
				}
				

			}catch (SocketException e) {
				EchoServer.log(" Sudden Closed by Client : ");
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
	}
}
