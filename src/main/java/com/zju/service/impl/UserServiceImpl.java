package com.zju.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zju.mapper.TicketMapper;
import com.zju.mapper.UserMapper;
import com.zju.model.Ticket;
import com.zju.model.User;
import com.zju.service.UserService;
import com.zju.utils.WendaUtil;


@Service()
public class UserServiceImpl implements UserService{
	
	@Resource
	UserMapper userMapper;
	@Resource
	TicketMapper ticketMapper;
	

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userMapper.selById(id);
	}

	@Override
	public Map<String, String> regist(String username, String password) {
		Random random = new Random();
		Map<String,String> map = new HashMap<>();
		//注册的简单验证
		if(StringUtils.isBlank(username)) {
			map.put("msg","用户名不能为空");
			return map;
		}
		
		if(StringUtils.isBlank(password)) {
			map.put("msg","密码不能为空");
			return map;
		}
		
		//用户名重复验证
		User user = userMapper.selByName(username);
		if(user!=null) {
			map.put("msg", "用户名已经存在");
			return map;
		}
		
		user = new User();
		//进行注册
		user.setName(username);
		//随机给的头像
		user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
		//生成随机字符做salt
		String salt = UUID.randomUUID().toString().substring(0, 5);
		user.setSalt(salt);
		//原始密码和salt相加后再进行MD5加密
		user.setPassword(WendaUtil.MD5(password+salt));
		int res = userMapper.insUser(user);
		if(res<1) {
			map.put("erro","数据库插入错误");
			return map;
		}
		//注册成功---向上层抛出ticket
		String ticket = addTicket(userMapper.selByName(username).getId());
		map.put("ticket", ticket);
		return map;
	}

	@Override
	public Map<String, String> login(String username, String password) {
		Random random = new Random();
		Map<String,String> map = new HashMap<>();
		//注册的简单验证
		if(StringUtils.isBlank(username)) {
			map.put("msg","用户名不能为空");
			return map;
		}
		
		if(StringUtils.isBlank(password)) {
			map.put("msg","密码不能为空");
			return map;
		}
		
		//用户名重复验证
		User user = userMapper.selByName(username);
		if(user==null) {
			map.put("msg", "用户不存在");
			return map;
		}
		//如果用户存在就验证密码
		if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
			map.put("msg", "密码错误");
			return map;
		}
		//登陆成功---向上层抛出ticket
		String ticket = addTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	
	
	
	
	private String addTicket(int userId) {
		Ticket loginTicket = new Ticket();
		
		loginTicket.setUserId(userId);
		loginTicket.setStatus(0);
		Date expired = new Date();
		//设置3天有效期
		expired.setTime(expired.getTime()+1000*3600*24*3);
		loginTicket.setExpired(expired);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
		ticketMapper.insTicket(loginTicket);
		return loginTicket.getTicket();
	}

	@Override
	public Map<String, String> logout(String ticket) {
		Map<String, String> map = new HashMap<>();
		int res = ticketMapper.updTicket(ticket, 1);
		if(res<1) {
			map.put("msg", "退出失败");
		}
		return map;
	}

}
