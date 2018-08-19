package com.liu.spring.sqlsession;

import java.lang.reflect.Proxy;

import com.liu.spring.mapper.userMapper;

public class MySqlSession {
	private Excutor excutor = new MyExcutor();
	MyConfiguration myConfiguration = new MyConfiguration();

	public <T> T getMapper(Class<T> cls){
		return  (T)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new MyMapperProxy(myConfiguration,this));
	}

	public Object selectOne(String sql, String valueOf) {
		// TODO Auto-generated method stub
		return excutor.query(sql, valueOf);
	}

}
