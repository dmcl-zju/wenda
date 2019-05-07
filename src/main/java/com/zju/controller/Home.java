package com.zju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class Home {
	
	@RequestMapping(value= {"/"})
	@ResponseBody
	public String test() {
		return "haha";
	}
	
	@RequestMapping(value= {"/home"})
	public String home() {
		return "home";
	}
}
