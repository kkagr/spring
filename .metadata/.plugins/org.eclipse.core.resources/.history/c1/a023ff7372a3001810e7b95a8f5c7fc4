package liu.mybatis.sqlsession;

import java.lang.reflect.Proxy;

public class MySqlSession {
	private Excutor excutor = new MyExcutor();
	private MyConfiguration myConfiguration = new MyConfiguration();
	public <T> T selectOne(String statement,String parameter){
		return excutor.query(statement, parameter);
	}
	public <T> T getMapper(Class<T> cls){
		return  (T)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new MyMapperProxy(myConfiguration,this));
	}
}
