package com.liu.spring.common;

public class characterUtil {
	public static String toLowerCaseFirstOne(String s){
		  if(Character.isLowerCase(s.charAt(0)))
		    return s;
		  else
		    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}


		//����ĸת��д
		public static String toUpperCaseFirstOne(String s){
		  if(Character.isUpperCase(s.charAt(0)))
		    return s;
		  else
		    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
}
