package test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalHost {

	public static void main(String[] args) {
	try {
		InetAddress inetAddress = InetAddress.getLocalHost();
		String hostName=inetAddress.getHostName();
		String hostAddress =inetAddress.getHostAddress();
		// 하나는 인터넷 하나는 virtual 때문에 2개 잡히게 되는데 virtual로 잡힘
		System.out.println(hostName + " : "+ hostAddress);
//		InetAddress[] inetAddressList=InetAddress.getAllByName(hostName);
//		for(InetAddress addr:inetAddressList)
//			System.out.println(addr.getHostAddress());
		
		byte[] adresses= inetAddress.getAddress(); 
		// 192.168.56.1 -> byte로 출력하면 -64 -88 56 1 나옴
		// byte
		for(byte address : adresses) {
			System.out.print(address & 0x00000ff);
			System.out.print('.');
		}
		
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
	}
	public static List<String> hostIPAddress() throws SocketException{
		List<String> result=new ArrayList<String>();
		for(NetworkInterface ifc:Collections.list(NetworkInterface.getNetworkInterfaces())) {
			//활성화 된 NIC가 아니면 무시
			if(ifc.isUp()==false) continue;
			
			//LoopBack(127.0.0.1)이면 무시
			if(ifc.isLoopback())  continue;
			
			//if (ifc.isVirtual() == true) {
			//	continue;
			//}
			
			for (InetAddress inetAddr : Collections.list(ifc.getInetAddresses())) {
				// Link Address(MAC Adress 무시)
				if (inetAddr.isLinkLocalAddress() == true)  continue;
				result.add(inetAddr.getHostAddress());
			}
			
		}
		
		return result;
		
		
	}

}
