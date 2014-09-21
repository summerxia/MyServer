package com.hmemcpy.spider;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hmemcpy.util.Util;

public class Spider {

	static int count = 0;

	public void download(String url, String re, boolean isDownload, String type) {
		String content = sendGet("http://baozoumanhua.com/tucao/hot/page/2");

		Pattern pattern = Pattern
				.compile("<div class=\"img-wrap\">.*?<img.*?src=\"(.*?)\".*?>.*?</div>");
		Matcher matcher = pattern.matcher(content);

		boolean isFind = matcher.find();
		
		List<String> urls = new ArrayList<String>();
		
		while (isFind) {
			String murl = matcher.group(1);
			System.out.println(murl);

			//Util.writeToXML(murl);
			
			urls.add(murl);
			
			if (isDownload) {
				try {
					downloadPictures(murl, "D:/baoman/", ++count + ".jpg");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			isFind = matcher.find();
		}
		
		Util.writeToXML(urls);
	}

	private String sendGet(String url) {
		String result = "";
		BufferedReader in = null;

		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));

			String line = "";

			while ((line = in.readLine()) != null)
				result += line;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	private void downloadPictures(String url, String path, String name)
			throws Exception {
		URL realurl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) realurl
				.openConnection();
		connection.connect();
		InputStream cin = connection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buff = new byte[1024];

		int len = 0;
		while ((len = cin.read(buff)) != -1) {
			baos.write(buff, 0, len);
		}
		cin.close();

		byte[] filedata = baos.toByteArray();
		baos.close();

		File dirs = new File(path);
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		File file = new File(path + name);

		System.out.println(path + name);

		FileOutputStream out = new FileOutputStream(file);
		out.write(filedata);
		out.flush();
		out.close();

	}

}
