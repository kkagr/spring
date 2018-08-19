package com.liu.spring.common;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LchClassXMLApplication {
	
	//xml映射路径
	private String xmlPath;
	private String findByElemntClass;
	public LchClassXMLApplication(String xmlPath){
		this.xmlPath=xmlPath;
	}
	
	public static void main(String[] args) {
		
		//获取bean
	}
	public Object getBean(String beanId) throws Exception{
		//首先读取xml
		if(StringUtils.isBlank(beanId)){
			throw new Exception("beanId不能为空");
		}
		List<Element> readerXml = readerXml();
		if(readerXml==null&&readerXml.isEmpty()){
			throw new Exception("配置文件中，没有bean信息");
		}
		String className = findByElemntClass(readerXml, beanId);
		if(StringUtils.isBlank(className)){
			throw new Exception("bean对应id没有找到class");
		}
		return newInstance(className);
	}
	
	public String findByElemntClass(List<Element> readerXml,String  beanId){
		for(Element element:readerXml){
			String xmlBeanId = element.attributeValue("id");
			if(StringUtils.isBlank(xmlBeanId)){
				continue;
			}
			if(xmlBeanId.equals(beanId)){
				String clazz = element.attributeValue("class");
				return clazz;
			}
		}
		return null;
	}
	public Object newInstance(String clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> forName = Class.forName(clazz);
		return forName.newInstance();
	}
	public List<Element> readerXml() throws DocumentException{
		SAXReader saxReader = new SAXReader();
		Document document =  saxReader.read(getResourceAsStream(xmlPath));
		Element rootElement = document.getRootElement(); 
		List elements = rootElement.elements();
		return elements;
	}
	
	
	private InputStream getResourceAsStream(String string) {
		// TODO Auto-generated method stub
		return this.getClass().getClassLoader().getResourceAsStream(string);
	}
}
