package com.liu.spring.sqlsession;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.liu.spring.bean.User;



public class MyExcutor implements Excutor{

	@Override
	public <T> T query(String sql, Object parameter) {
		// TODO Auto-generated method stub
		Connection connection = getConnection();
		ResultSet set = null;
		PreparedStatement pre = null;
		try {
			pre = connection.prepareStatement(sql);
			pre.setString(1, parameter.toString());
			set = pre.executeQuery();
			User user = new User();
			while(set.next()){
				user.setId(set.getString(1));
				user.setUsername(set.getString(2));
				user.setPassword(set.getString(3));
			}
			return (T)user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
					if(set!=null){
					set.close();
					} 
					if(pre!=null){
						pre.close();
						} 
					if(connection!=null){
						connection.close();
						} 
					
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}
	public Connection getConnection(){
		MyConfiguration myConfiguration = new MyConfiguration();
		Connection connection = myConfiguration.build("config.xml");
		return connection;
	}

}
