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
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP="192.168.1.36";
	private static final int SERVER_PORT=7000;
	public static void main(String[] args) {
		Socket socket =null;
		Scanner scanner=null; 
		try {
			//1. 소켓 생성 / Scanner 생성(표준입출력 연결)
			scanner=new Scanner(System.in);
			  socket= new Socket();
			//2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			// cmd 가서 d: -> cafe24 eclipse-workspace neetwork bin java test.TCPServer
			
			//3. IOStream 받아오기
			InputStream is= socket.getInputStream();
			OutputStream os=socket.getOutputStream();

			BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
			PrintWriter pr=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			
			while(true) {
				//5. keyboard 입력받기
				System.out.print(">>");
				String line=scanner.nextLine();
				if("quit".contentEquals(line)) {
					break;
				}
				
				//6. 데이터 쓰기
				pr.println(line);
				
				//7. 데이터 읽기
				String data =br.readLine();
				if(data==null) {
					log("closed by server"); break;
				}
				
				//8. console 출력
				System.out.println("<<"+data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				 try {
					 if(scanner!=null) {
						 scanner.close();
					 }
					 if(socket!=null && !socket.isClosed() ) {
					socket.close();
					 }
				} catch (IOException e) {
					e.printStackTrace();
			 }
		}
	}
	public static void log(String log) {
		System.out.println("[Client] "+log);
	}
	
}
