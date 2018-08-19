package com.liu.spring.sqlsession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import com.liu.spring.config.Function;
import com.liu.spring.config.MapperBean;

public class MyMapperProxy implements InvocationHandler{
	private MySqlSession mySqqlsession;
	private MyConfiguration myConfiguration;
	public MyMapperProxy( MyConfiguration myConfiguration,MySqlSession mySqqlsession) {
		this.mySqqlsession = mySqqlsession;
		this.myConfiguration = myConfiguration;
	}
	@Override
	public Object invoke(Object var1, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		MapperBean readMapper = myConfiguration.readMapper("userMapper.xml");
		if(!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
			return null;
		}
		List<Function>  functionList =readMapper.getList();
		if(functionList!=null&&functionList.size()>0){
			for(Function function:functionList){
				if(method.getName().equals(function.getFuncName())){}
				return mySqqlsession.selectOne(function.getSql(), String.valueOf(args[0]));
			}
		}
		return null;
	}
}
