package com.liu.spring.sqlsession;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.liu.spring.common.ReadXmlUtil;
import com.liu.spring.config.Function;
import com.liu.spring.config.MapperBean;

public class MyConfiguration {
	private static ClassLoader loader = ClassLoader.getSystemClassLoader();
	public MapperBean readMapper(String path) {
		MapperBean mapper = new MapperBean();
		InputStream stream = ReadXmlUtil.getResourceAsStream(path);
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(stream);
			Element root = document.getRootElement();
			mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
			List<Function> list = new ArrayList<Function>();
			for(Iterator rootIter=root.elementIterator();rootIter.hasNext();){
				Function function = new Function();
				Element element = (Element) rootIter.next();
				String sqlType = element.getName().trim();
				String funcName = element.attributeValue("id").trim();
				String sql = element.getText();
				String resultType = element.attributeValue("resultType").trim();
				function.setFuncName(funcName);
				function.setSqlType(sqlType);
				Object newInstance = null;
				try {
					newInstance =Class.forName(resultType).newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				function.setResultType(resultType);
				function.setSql(sql);
				list.add(function);
			}
			mapper.setList(list);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("database no evalDatesource");
		}
		return mapper;
	}

	public Connection build(String resource) {
		InputStream stream = ReadXmlUtil.getResourceAsStream(resource);
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(stream);
			Element root = document.getRootElement();
			return evalDatesource(root);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("database no evalDatesource");
		}
	}
	public Connection evalDatesource(Element node){
		if(!node.getName().equals("database")){
			throw new RuntimeException("root no database");
		}
		String driverClassName = null;
		String url = null;
		String username = null;
		String password = null ;
		for(Object item:node.elements("property")){
			Element i = (Element) item;
			String value = getValue(i);
			String name = i.attributeValue("name");
			if(name == null||value ==null){
				throw new RuntimeException("database no name");
			}
			switch (name){
			case "url":url=value;break;
			case "username": username =value;break;
			case "password": password = value;break;
			case "driverClassName": driverClassName = value;break;
			default:throw new RuntimeException("database no name");
			}
		}
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public String getValue(Element node){
		return node.hasContent()?node.getText():node.attributeValue("value");
	}

}
