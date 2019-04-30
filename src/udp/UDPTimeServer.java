package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer  {
	public static final int PORT=6000;
	public static final int BUFFER_SIZE=1024;

public static void main(String[] args) {
	DatagramSocket datagrameSocket=null;
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
	String date=format.format(new Date());
	
	try {
		// UDP 소켓 생성
		datagrameSocket=new DatagramSocket(PORT);
		while(true) {
		log("packet 수신대기");
		// 데이터 수신
			DatagramPacket receivePacket=new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			datagrameSocket.receive(receivePacket);
			
			// 수신 데이터 출력
			String message=new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
			log("Packet Receive : "+message);
			if("".equals(message)) {
				System.out.println(date);
				
			}
			//데이터 보내기
			DatagramPacket sendPacket=new DatagramPacket(date.getBytes(), 
					date.length(),receivePacket.getAddress(),receivePacket.getPort()
					);
			 
			datagrameSocket.send(sendPacket);
		}
	} catch (SocketException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
}
public static void log(String log) {
	System.out.println("[Server] "+log);
}
}
