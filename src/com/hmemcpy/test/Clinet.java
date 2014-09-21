package com.hmemcpy.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Clinet {
	Socket socket = null;
	public void Connect(){
		try {
			 socket = new Socket("172.29.201.1",8821);
			 InputStream in = socket.getInputStream();
			 
			 BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			 
			 String temp = "";
			 StringBuilder sb = new StringBuilder();
			 
			 while((temp = reader.readLine()) != null){
				 sb.append(temp);
			 }
			 
			 
			 System.out.println(sb.toString());
			 
			 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public static void main(String[] args){
		new Clinet().Connect();
	}

}
