package com.liu.spring.service.impl;

import com.liu.spring.annotion.LchServiceAnnotion;
import com.liu.spring.service.TestService;
import com.liu.spring.service.TestService1;

@LchServiceAnnotion
public class TestServiceImpl1 implements TestService1{
	public  void add(){
		System.out.println("��������");
	}
}
