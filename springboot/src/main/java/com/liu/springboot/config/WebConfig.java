package com.liu.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 
* @ClassName: WebConfig 
* @Description: springmvc������Ϣ
* @author kkagr
* @date 2018��8��20�� ����10:32:08 
*
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.liu.springboot.controller"})
public class WebConfig extends WebMvcConfigurerAdapter{
	//��Ҫ������ͼת����
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setExposeContextBeansAsAttributes(true);
		return viewResolver;
		
	}
}
