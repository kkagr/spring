package com.liu.spring.common;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.commons.lang.StringUtils;

import com.liu.spring.annotion.LchResource;
import com.liu.spring.annotion.LchServiceAnnotion;

public class LchClassXMLApplication {
	//ɨ����Χ
	private String packageName;
	private ConcurrentHashMap<String, Object> beans=new ConcurrentHashMap<>();
	public LchClassXMLApplication(String packageName) throws Exception{
		this.packageName=packageName;
		 initBeans();
		 for(Entry<String, Object> entry:beans.entrySet()){
			 Object value = entry.getValue();
			 assignAnnotion(value);
		 }
	}
	public void initBeans() throws Exception{
		List<Class<?>> classes = ClassUtil.getClasses(packageName);
		findClassAnnotion(classes);
	}
	public void findClassAnnotion(List<Class<?>> classes) throws Exception{
		for(Class<?> clazz:classes){
			LchServiceAnnotion annotation = clazz.getAnnotation(LchServiceAnnotion.class);
			if(annotation!=null){
				 String className = clazz.getSimpleName();
				 String beanId = toUpperCaseFirstOne(className);
				 Object newInstance = newInstance(clazz);
				 beans.put(beanId, newInstance);
				 continue;
			}
			
		}
	}
	
	public void assignAnnotion(Object object) throws Exception{
		Field[] declareField = object.getClass().getDeclaredFields();
		for(Field field :declareField){
			LchResource lchresource = field.getAnnotation(LchResource.class);
			if(lchresource!=null){
				String fieldName = field.getName();
				Object bean = beans.get(fieldName);
				if(bean!=null){
					field.setAccessible(true);
					field.set(object, bean);
				}
			}
		}
	}
	
	//����ĸת��д
	public static String toUpperCaseFirstOne(String str){
		char[] chars = str.toCharArray();
		chars[0]+=32;
		String string = new String(chars);
		return string;
	}
	public Object getBean(String beanId) throws Exception{
		if(StringUtils.isBlank(beanId)){
			throw new Exception("beanId����Ϊ��");
		}
		Object object= beans.get(beanId);
		if(object == null){
			throw new Exception("class not fount");
		}
		return object;
	}
	public Object newInstance(Class<?> classInfo) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return classInfo.newInstance();
	}
}
