package com.liu.springmvc.annotion;

import java.lang.annotation.*;

/**
* @author kkagr
* @version ����ʱ�䣺2018��5��6�� ����10:04:31
* ��˵��
*/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LCHController {
	  String value() default "";
}
