package com.liu.mybatis.test;

import com.liu.mybatis.bean.User;
import com.liu.mybatis.mapper.userMapper;
import com.liu.mybatis.sqlsession.MySqlSession;

public class test {
	public static void main(String[] args) {
		//首先创建sqlsession
		//获取mapper接口
		//执行查询（通过代理，反射执行，封装对象）
		MySqlSession mysqlSession = new MySqlSession();
		userMapper userMapper1= mysqlSession.getMapper(userMapper.class);
		User user = userMapper1.getuserbyId("1");
		System.out.println(user);
	}
}
