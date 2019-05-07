package com.zju.service;

import java.util.Map;

/**
 * 用户注册功能
 * @author lin
 *
 */
public interface RegistService {

	public Map<String,String> regist(String userName,String password);
}
