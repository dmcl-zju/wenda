package com.zju.utils;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean{
	
	Logger logger = Logger.getLogger(JedisAdapter.class);
	JedisPool pool;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool = new JedisPool("localhost",6379);
	}

//-------------------------------------------封装一些常用的命令----------------------------------------------------------------------------
	//添加字符串
	public void set(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	//获取字符串
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return null;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	
	//添加集合元素
	public long sadd(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return 0;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	
	//删除集合元素
	public long srem(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srem(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return 0;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	//是否是集合成员
	public boolean sismember(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key,value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return false;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	//集合大小
	public long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.scard(key);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return 0;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	
	//list头部插入（左边）
	public long lpush(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpush(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return 0;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	//list尾部阻塞弹出（右侧）
	public List<String> brpop(int timeout,String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.brpop(timeout, key);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis异常："+e.getMessage());
			return null;
		}finally {
			if(null!=jedis) {
				jedis.close();
			}
		}
	}
	

}
