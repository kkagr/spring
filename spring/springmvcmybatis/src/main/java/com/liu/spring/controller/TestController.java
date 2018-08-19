package com.liu.spring.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.spring.bean.User;
import com.liu.spring.mapper.userMapper;
import com.liu.spring.sqlsession.MySqlSession;
import com.liu.spring.annotation.LCHAutorited;
import com.liu.spring.annotation.LCHController;
import com.liu.spring.annotation.LCHRequestMapping;
import com.liu.spring.annotation.LCHRequestParam;
import com.liu.spring.service.TestService;

@LCHController
@LCHRequestMapping("/test")
public class TestController {
	@LCHAutorited
	private TestService testService;

	@LCHRequestMapping("/test")
	public String test(HttpServletRequest requset,
						   HttpServletResponse response,
						   @LCHRequestParam(value ="name") String name) throws IOException{
		MySqlSession mysqlSession = new MySqlSession();
		userMapper userMapper1= mysqlSession.getMapper(userMapper.class);
		User user = userMapper1.getuserbyId("1");
		System.out.println(user.toString());
		String data ="te323st";
		requset.setAttribute("test", data);
		requset.setAttribute("user", user);
		return "test";
	}
}
