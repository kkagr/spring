package com.liu.springboot.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	/**
	 * ����������Ϣspring����
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new  Class[]{RootConfig.class};
	}
	/**
	 * springmvc������Ϣ
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new  Class[]{WebConfig.class};
	}
	/**
	 * springmvc����urlӳ��
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

}
