package com.hmemcpy.test;

import java.io.IOException;

import com.hmemcpy.server.Server;
import com.hmemcpy.spider.Spider;

public class Main {
	public static void main(String[] args) throws IOException{
		new Spider().download("", "", false, "");
		
//		File file = new File("");
//		
//		System.out.println(file.getAbsolutePath());
		
		
		new Server().service();
		
	}
}
