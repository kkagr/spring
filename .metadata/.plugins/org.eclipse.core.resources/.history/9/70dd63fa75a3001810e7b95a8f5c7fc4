package com.liu.mybatis.test;

import com.liu.mybatis.bean.User;
import com.liu.mybatis.mapper.userMapper;
import com.liu.mybatis.sqlsession.MySqlSession;

public class test {
	public static void main(String[] args) {
		MySqlSession mysqlSession = new MySqlSession();
		userMapper userMapper1= mysqlSession.getMapper(userMapper.class);
		User user = userMapper1.getuserbyId("1");
		System.out.println(user);
	}
}
