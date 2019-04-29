package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class PhoneList01 {
public static void main(String[] args) {
	//기반 스트림
	BufferedReader br=null; 
	try {
		//보조스트림1 (byte -> char )
 		 br=new BufferedReader(new InputStreamReader(new FileInputStream("phone.txt"),"UTF-8"));
 		 String line=null;
 		 StringTokenizer st;
 		 while((line=br.readLine())!=null) {
 			 st=new StringTokenizer(line);
 			 int index=0;
 			 while(st.hasMoreElements()) {
 				 String token=st.nextToken();
 				 if(index==0) { // name
 					 System.out.print (token +" : ");
 				 }else  { // tel num
 					 if(index==1) {
 						System.out.print (token +"-");
 					 }else if(index==2) {
 						System.out.print (token +"-");
 					 }else {
 						 System.out.println (token);
 						 index=0;
 					 }
 				 }
 				 index++;
 			 }
 		 }
	
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
			try {
				if(br!=null)
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
}
