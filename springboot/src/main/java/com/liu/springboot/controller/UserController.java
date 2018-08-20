package com.liu.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liu.springboot.service.UserService;

@Controller
public class UserController {
	@Autowired UserService userService;
	@RequestMapping("/pageIndex")
	public String pageIndex(){
		String result = userService.service();
		return "pageIndex";
	}
}
