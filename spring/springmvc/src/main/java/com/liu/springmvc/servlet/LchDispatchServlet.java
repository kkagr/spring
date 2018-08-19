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
* @Description: TODO(�Զ���ǰ�˿�����) 
* @author kkagr
* @date 2018��8��17�� ����11:19:40 
*
 */
public class LchDispatchServlet extends HttpServlet{
	/*1.����һ��ǰ�˿�����LchDispatchServlet���������е�����springmvc����servletʵ�֣�
	  2.��ʼ��������дservlet�е�init��������
	    2.1.��ɨ����Χ�ڵ������࣬ע�뵽springmvc������
	    2.2.��urlӳ��ͷ������й���
	    	2.2.1�ж������Ƿ���ע�⣬ʹ��java�������ѭ�������������ж������Ƿ���ע�⣬���з�װurl�ͷ���
	  3.����������дGet����Post����
	  	3.1��ȡ����url��ȥurlBeans�����л�ȡʾ�����󣬻�ȡʵ������� ������urlMothod���ϻ�ȡ�������ƣ�ʹ�÷������ִ��
	*/
	public ConcurrentHashMap<String, Object> springmvcBeans = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, String> methodBeans = new ConcurrentHashMap<>();
	
	
	@Override
	public void init() throws ServletException {
		//1.��ȡ��ǰ�������е���
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
		// 1.��ȡ����url��ַ
		String requestURI = req.getRequestURI();
		if(StringUtils.isBlank(requestURI)){
			return;
		}
		//2.��map�����л�ȡ���ƶ���
		Object object = urlBeans.get(requestURI);
		if(object == null){
			resp.getWriter().println("not found 404 url");
		}
		//3.ʹ��url��ַ��ȡ����
		String methodName = methodBeans.get(requestURI);
		if(StringUtils.isBlank(requestURI)){
			resp.getWriter().println("not found method");
		}
		//4.ʹ��java�ķ�����Ƶ���
		String resultPage = (String) methodInvoke(object, methodName);
		//������ͼת������Ⱦҳ��չʾ
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
	
	//2��ɨ����Χ�������� ��ע�뵽springmvc�������У������Map�����У�keyΪĬ������Сд��valueΪ����
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
	//3��urlӳ��ͷ������й���
	public void handlerMapping(){
		//1.��ȡspringmvc bean�����ж������Ƿ���urlӳ��ע��
		for(Map.Entry<String, Object> mvcbean:springmvcBeans.entrySet()){
			//2.�������еķ������Ƿ���urlӳ��ע��
			//����bean�Ķ���
			Object object = mvcbean.getValue();
			//3.�ж������Ƿ���urlӳ��ע��
			Class<?> classInfo = object.getClass();
			LCHRequestMapping contorlRequestMapping = classInfo.getAnnotation(LCHRequestMapping.class);
			String baseUrl = "/springmvc";
			if(contorlRequestMapping != null){
				baseUrl = baseUrl+contorlRequestMapping.value();
			}
			//4.�жϷ��������Ƿ���urlӳ��ע��
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