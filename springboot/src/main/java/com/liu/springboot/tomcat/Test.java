package com.liu.springboot.tomcat;

import java.nio.charset.StandardCharsets;

import javax.swing.tree.FixedHeightLayoutCache;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import com.liu.springboot.servlet.IndexServlet;
/**
 * 使用java语言操作Tomcat
 * Tomcat底层最终执行servlet程序 
* @ClassName: Test 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author kkagr
* @date 2018年8月19日 下午11:50:36 
*
 */
public class Test {
	private static int PORT = 8080;
	private static String CONTEXT_PATH ="/comliu";
	private static String SERVLET_NAME ="/indexServlet";
	public static void main(String[] args) throws LifecycleException, InterruptedException {
		
		//创建tomcat服务器
		Tomcat tomcat = new Tomcat();
		//指导端口
		tomcat.setPort(PORT);
		//是否设置自动部署
		tomcat.getHost().setAutoDeploy(false);
		//创建上下文
		StandardContext standardContext = new StandardContext(); 
		standardContext.setPath(CONTEXT_PATH);
		//监听上下文
		standardContext.addLifecycleListener(new FixContextListener());
		//tomcat容器添加standardContext
		tomcat.getHost().addChild(standardContext);
		//创建servlet
		tomcat.addServlet(CONTEXT_PATH, SERVLET_NAME, new IndexServlet());
		//servlet映射
		standardContext.addServletMappingDecoded("/index", SERVLET_NAME);
		tomcat.start();
		Thread.sleep(5000);
		//
		tomcat.getServer().await();
		System.out.println("tomcat启动成功");
	}
}
