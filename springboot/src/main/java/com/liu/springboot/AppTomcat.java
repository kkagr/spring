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
		//使用java内置tomcat运行SpringMVC框架
		//原理tomcat加载到springMVC注解启动方式，就会创建springMVC容器
		//创建tomcat服务器
			start();
	}

	private static void start() throws ServletException, LifecycleException {
		//创建tomcat服务器
		Tomcat tomcat = new Tomcat();
		//指导端口
		tomcat.setPort(9090);
		//是否设置自动部署
		tomcat.getHost().setAutoDeploy(false);
		//读取项目路径 ，加载静态资源
		StandardContext standardContext = (StandardContext) tomcat.addWebapp("/", new File("src/main").getAbsolutePath());
		//禁止重新载入
		standardContext.setReloadable(false);
		//class文件读取地址
		File additionWebInfo = new File("target/classes");
		//创建WebRoot
		WebResourceRoot resources  = new StandardRoot(standardContext);
		//tomcat读取内部class执行
		resources.addPostResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfo.getAbsolutePath(), "/"));
		tomcat.start();
		//
		tomcat.getServer().await();
		System.out.println("tomcat启动成功");
	}
}
