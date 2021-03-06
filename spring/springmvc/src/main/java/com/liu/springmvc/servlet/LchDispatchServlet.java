package com.liu.springmvc.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.junit.runner.Request;

import com.liu.springmvc.annotion.LCHController;
import com.liu.springmvc.annotion.LCHRequestMapping;
import com.liu.springmvc.common.ClassUtil;
import com.liu.springmvc.common.characterUtil;

/**
 * 
* @ClassName: LchDispatchServlet 
* @Description: TODO(自定义前端控制器) 
* @author kkagr
* @date 2018年8月17日 下午11:19:40 
*
 */
public class LchDispatchServlet extends HttpServlet{
	/*1.创建一个前端控制器LchDispatchServlet，拦截所有的请求（springmvc基于servlet实现）
	  2.初始化操作重写servlet中的init（）方法
	    2.1.将扫包范围内的所有类，注入到springmvc容器中
	    2.2.将url映射和方法进行关联
	    	2.2.1判断类上是否有注解，使用java反射机制循环遍历方法，判断类上是否有注解，进行封装url和方法
	  3.处理请求重写Get或者Post方法
	  	3.1获取请求url，去urlBeans集合中获取示例对象，获取实例对象后 ，调用urlMothod集合获取方法名称，使用反射机制执行
	*/
	public ConcurrentHashMap<String, Object> springmvcBeans = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, String> methodBeans = new ConcurrentHashMap<>();
	
	
	@Override
	public void init() throws ServletException {
		//1.获取当前包下所有的类
		List<Class<?>> classes = ClassUtil.getClasses("com.liu.springmvc.controller");
		try {
			findClassMVCAnnotion(classes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handlerMapping();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.获取请求url地址
		String requestURI = req.getRequestURI();
		if(StringUtils.isBlank(requestURI)){
			return;
		}
		//2.从map集合中获取控制对象
		Object object = urlBeans.get(requestURI);
		if(object == null){
			resp.getWriter().println("not found 404 url");
		}
		//3.使用url地址获取对象
		String methodName = methodBeans.get(requestURI);
		if(StringUtils.isBlank(requestURI)){
			resp.getWriter().println("not found method");
		}
		//4.使用java的反射机制调用
		String resultPage = (String) methodInvoke(object, methodName);
		//调用视图转换器渲染页面展示
		extResourceViewResolver(resultPage, req, resp);
		//resp.getWriter().println(resultPage);
	}
	private void extResourceViewResolver(String pageName,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		String  prefix = "/";
		String suffix = ".jsp";
		req.getRequestDispatcher(prefix+pageName+suffix).forward(req, resp);;
	}
	
	private Object methodInvoke(Object object,String methodName){
		try {
			Class<? extends Object> classInfo = object.getClass();
			Method method = classInfo.getMethod(methodName);
			Object result = method.invoke(object);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//2将扫包范围的所有类 ，注入到springmvc的容器中，存放在Map集合中，key为默认类名小写，value为对象
	private void findClassMVCAnnotion(List<Class<?>> classes) throws Exception {
		for(Class<?> classInfo:classes){
			LCHController lchController = classInfo.getAnnotation(LCHController.class);
			if(lchController != null){
				String beanId = characterUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
				Object object = ClassUtil.newInstance(classInfo);
				springmvcBeans.put(beanId, object);
			}
		}
		
	}
	//3将url映射和方法进行关联
	public void handlerMapping(){
		//1.获取springmvc bean容器判断上属是否有url映射注解
		for(Map.Entry<String, Object> mvcbean:springmvcBeans.entrySet()){
			//2.遍历所有的方法上是否有url映射注解
			//遍历bean的对象
			Object object = mvcbean.getValue();
			//3.判断类上是否有url映射注解
			Class<?> classInfo = object.getClass();
			LCHRequestMapping contorlRequestMapping = classInfo.getAnnotation(LCHRequestMapping.class);
			String baseUrl = "/springmvc";
			if(contorlRequestMapping != null){
				baseUrl = baseUrl+contorlRequestMapping.value();
			}
			//4.判断方法上面是否有url映射注解
			Method[] declaredMethods = classInfo.getDeclaredMethods();
			for(Method method:declaredMethods){
				LCHRequestMapping methodRequestMapping = method.getAnnotation(LCHRequestMapping.class);
				if(methodRequestMapping != null){
					String methodUrl = baseUrl+methodRequestMapping.value();
					urlBeans.put(methodUrl, object);
					methodBeans.put(methodUrl, method.getName());
				}
			}
		}
	}

}
