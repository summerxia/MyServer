package com.hmemcpy.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Util {

	public static void writeToXML(List<String> info) {

		SAXReader reader = new SAXReader();
		Document document = null;
		Element root = null;
		try {
			document = reader.read(new File("list.xml"));
			root = document.getRootElement();
			root.clearContent();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (String s : info) {
			root.addElement("info").addAttribute("url", s);
		}
		try {
			XMLWriter writer = new XMLWriter(new FileWriter("list.xml"));
			writer.write(document);
			writer.close();
		} catch (IOException e) {

		}
	}

	public static void clearXML() {
		SAXReader reader = new SAXReader();
		Document document = null;
		Element root = null;
		try {
			document = reader.read(new File("list.xml"));
			root = document.getRootElement();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		root.clearContent();

	}
}
