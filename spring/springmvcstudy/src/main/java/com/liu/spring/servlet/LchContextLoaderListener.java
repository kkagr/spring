package com.liu.spring.servlet;

import java.util.HashMap;
import java.util.List;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.liu.spring.common.ReadXmlUtil;
import com.liu.spring.common.SystemParam;

public class LchContextLoaderListener implements ServletContextListener{
	private String[] configLocations;
	@Override
	public void contextDestroyed(ServletContextEvent var1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		String initParameter = event.getServletContext().getInitParameter("contextConfigLocation");
		String[] locations = initParameter.substring(10).split(";");
		try {
			List<Element> readerXml = ReadXmlUtil.readerXml(locations[0]);
			SystemParam.springElement=readerXml;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
