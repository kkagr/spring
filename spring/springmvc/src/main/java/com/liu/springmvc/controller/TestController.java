package com.liu.springmvc.controller;

import com.liu.springmvc.annotion.LCHController;
import com.liu.springmvc.annotion.LCHRequestMapping;

@LCHController
@LCHRequestMapping(value="/test")
public class TestController {
	@LCHRequestMapping(value="/test")
	public String test(){
		return "test";
	}

}
