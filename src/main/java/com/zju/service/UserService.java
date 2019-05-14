package com.zju.service;

import java.util.Map;

import com.zju.model.User;

public interface UserService {
	
	//由ID获取用户对象
	public User getUserById(int id);
	
	//通过用户名查找
	public User getUserByName(String name);
	
	//用户注册
	public Map<String,String> regist(String username,String password);
	
	//用户登录
	public Map<String,String> login(String username,String password);
	
	//用户退出登录
	public Map<String,String> logout(String ticket);
	
}
