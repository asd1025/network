package util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NsLookup {

	public static void main(String[] args) {
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				 
				String hostName;
				try {
					while(true) {
					 System.out.print(">");
					hostName=br.readLine();
					if(hostName.equals("exit")) break;
					InetAddress[] inetAdresses = InetAddress.getAllByName(hostName);
					for(InetAddress addr:inetAdresses)
						System.out.println(addr.getHostName()+" : "+addr.getHostAddress());
					}		
					 
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}finally {
			try {
			if(br!=null){
					br.close();
			}
			 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
