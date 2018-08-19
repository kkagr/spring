package com.liu.spring.test;


import com.liu.spring.common.LchClassXMLApplication;
import com.liu.spring.service.TestService;
import com.liu.spring.service.impl.TestServiceImpl;

public class ClassXmlTest {
	/**
	 * 
	* @author kkagr
	 * @throws Exception 
	* @date: 2018年8月16日 上午12:13:01
	 */
	public static void main(String[] args) throws Exception {
		LchClassXMLApplication lchClassXMLApplication = new LchClassXMLApplication("com.liu.spring.service.impl");
		TestServiceImpl bean = (TestServiceImpl) lchClassXMLApplication.getBean("testServiceImpl");
		bean.add();
	}

}
