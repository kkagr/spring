package com.liu.springboot.tomcat;

import java.nio.charset.StandardCharsets;

import javax.swing.tree.FixedHeightLayoutCache;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import com.liu.springboot.servlet.IndexServlet;
/**
 * ʹ��java���Բ���Tomcat
 * Tomcat�ײ�����ִ��servlet���� 
* @ClassName: Test 
* @Description: TODO(������һ�仰��������������) 
* @author kkagr
* @date 2018��8��19�� ����11:50:36 
*
 */
public class Test {
	private static int PORT = 8080;
	private static String CONTEXT_PATH ="/comliu";
	private static String SERVLET_NAME ="/indexServlet";
	public static void main(String[] args) throws LifecycleException, InterruptedException {
		
		//����tomcat������
		Tomcat tomcat = new Tomcat();
		//ָ���˿�
		tomcat.setPort(PORT);
		//�Ƿ������Զ�����
		tomcat.getHost().setAutoDeploy(false);
		//����������
		StandardContext standardContext = new StandardContext(); 
		standardContext.setPath(CONTEXT_PATH);
		//����������
		standardContext.addLifecycleListener(new FixContextListener());
		//tomcat�������standardContext
		tomcat.getHost().addChild(standardContext);
		//����servlet
		tomcat.addServlet(CONTEXT_PATH, SERVLET_NAME, new IndexServlet());
		//servletӳ��
		standardContext.addServletMappingDecoded("/index", SERVLET_NAME);
		tomcat.start();
		Thread.sleep(5000);
		//
		tomcat.getServer().await();
		System.out.println("tomcat�����ɹ�");
	}
}
