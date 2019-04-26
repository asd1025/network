package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PhoneList02 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("Phone.txt"));
			while(sc.hasNextLine()) {
				String name=sc.next();
				String phone01=sc.next();
				String phone02=sc.next();
				String phone03=sc.next();
				System.out.println(name+" : "+phone01+"-"+phone02+"-"+phone03);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}

	}
}
