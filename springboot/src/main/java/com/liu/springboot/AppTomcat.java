package com.liu.springboot;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResource;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import com.liu.springboot.servlet.IndexServlet;
import com.liu.springboot.tomcat.FixContextListener;

public class AppTomcat {
	
	public static void main(String[] args) throws ServletException, LifecycleException {
		//ʹ��java����tomcat����SpringMVC���
		//ԭ��tomcat���ص�springMVCע��������ʽ���ͻᴴ��springMVC����
		//����tomcat������
			start();
	}

	private static void start() throws ServletException, LifecycleException {
		//����tomcat������
		Tomcat tomcat = new Tomcat();
		//ָ���˿�
		tomcat.setPort(9090);
		//�Ƿ������Զ�����
		tomcat.getHost().setAutoDeploy(false);
		//��ȡ��Ŀ·�� �����ؾ�̬��Դ
		StandardContext standardContext = (StandardContext) tomcat.addWebapp("/", new File("src/main").getAbsolutePath());
		//��ֹ��������
		standardContext.setReloadable(false);
		//class�ļ���ȡ��ַ
		File additionWebInfo = new File("target/classes");
		//����WebRoot
		WebResourceRoot resources  = new StandardRoot(standardContext);
		//tomcat��ȡ�ڲ�classִ��
		resources.addPostResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfo.getAbsolutePath(), "/"));
		tomcat.start();
		//
		tomcat.getServer().await();
		System.out.println("tomcat�����ɹ�");
	}
}