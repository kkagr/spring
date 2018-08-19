package com.liu.spring.test;

import com.liu.spring.common.LchClassXMLApplication;
import com.liu.spring.service.TestService;

public class ClassXmlTest {
	/**
	 * 
	* @author kkagr
	* @date: 2018��8��16�� ����12:13:01
	 */
	public static void main(String[] args) {
		LchClassXMLApplication xml = new LchClassXMLApplication("beans.xml");
		TestService testService;
		try {
			testService = (TestService) xml.getBean("testService");
			testService.add();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
