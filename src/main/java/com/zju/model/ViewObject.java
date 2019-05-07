package com.zju.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放和前端交互的对象
 * @author lin
 *
 */
public class ViewObject {
	//用来存放交互对象
	private Map<String,Object> map = new HashMap<>();
	
	public void set(String key,Object value) {
		map.put(key,value);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
}
