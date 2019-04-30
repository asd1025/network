package udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class UDPTimeClient  {
	private static final String SERVER_ADDRESS="192.168.1.36";
public static void main(String[] args) {
	// UDP 소켓 생성
	DatagramSocket datagramSocket=null;
	Scanner scanner=null;
	
	try {
		scanner=new Scanner(System.in);
		// 데이터 보내기
		datagramSocket=new DatagramSocket();
		while(true) {
		String message=scanner.nextLine();
		byte[] sendData=message.getBytes("UTF-8");
		DatagramPacket sendPacket=new DatagramPacket(sendData, sendData.length,new InetSocketAddress(SERVER_ADDRESS, UDPTimeServer.PORT));
			datagramSocket.send(sendPacket);
			
		// 데이터 받기
		DatagramPacket receivePacket=new DatagramPacket(new byte[UDPTimeServer.BUFFER_SIZE], UDPTimeServer.BUFFER_SIZE);
		datagramSocket.receive(receivePacket);
		
		// 메세지 출력
		String data=new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
		System.out.println("<<"+data);
		}
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
}
public static void log(String log) {
	System.out.println("[Client] "+log);
}
}
