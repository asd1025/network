package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP="192.168.1.36";
	public static void main(String[] args) {
		DatagramSocket socket =null;
		Scanner scanner=null; 
		try {
			//1. Scanner 생성(표준입출력 연결)
			scanner=new Scanner(System.in);
			//2. 소켓생성
			  socket= new DatagramSocket();
			
			while(true) {
				//3. keyboard 입력받기
				System.out.print(">>");
				String line=scanner.nextLine();
				if("quit".contentEquals(line)) {
					break;
				}
				
				//4. 데이터 쓰기
				 byte[] sendData= line.getBytes();
				 DatagramPacket sendPacket=new DatagramPacket(sendData, sendData.length, new InetSocketAddress(SERVER_IP,UDPEchoServer.PORT));
				 socket.send(sendPacket);
				
				//5. 데이터 읽기
				  DatagramPacket receivePacket=new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
					 socket.receive(receivePacket); //blocking
					 String message=new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
				//8. console 출력
				System.out.println("<<"+message);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				 if(scanner!=null) {
					 scanner.close();
				 }
				 if(socket!=null && !socket.isClosed() ) {
				socket.close();
				 }
		}
	}
	public static void log(String log) {
		System.out.println("[Client] "+log);
	}
	
}
