package com.liu.springboot.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	/**
	 * º”‘ÿ≈‰÷√–≈œ¢spring∫À–ƒ
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new  Class[]{RootConfig.class};
	}
	/**
	 * springmvc≈‰÷√–≈œ¢
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new  Class[]{WebConfig.class};
	}
	/**
	 * springmvc¿πΩÿurl”≥…‰
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

}
