package com.liu.spring.common;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXmlUtil {
	public static List<Element> readerXml(String xmlPath) throws DocumentException{
		SAXReader saxReader = new SAXReader();
		Document document =  saxReader.read(getResourceAsStream(xmlPath));
		Element rootElement = document.getRootElement(); 
		List elements = rootElement.elements();
		return elements;
	}
	public static InputStream getResourceAsStream(String string) {
		ReadXmlUtil readXmlUtil = new ReadXmlUtil();
		return readXmlUtil.getClass().getClassLoader().getResourceAsStream(string);
	}
}
