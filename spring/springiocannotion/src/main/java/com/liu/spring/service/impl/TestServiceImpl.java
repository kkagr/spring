package com.liu.spring.service.impl;

import com.liu.spring.annotion.LchResource;
import com.liu.spring.annotion.LchServiceAnnotion;
import com.liu.spring.service.TestService;
import com.liu.spring.service.TestService1;

@LchServiceAnnotion
public class TestServiceImpl implements TestService{
	@LchResource
	public TestService1 testServiceImpl1;
	public  void add(){
		testServiceImpl1.add();
		System.out.println("��������");
	}
}
