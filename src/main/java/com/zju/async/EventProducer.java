package com.zju.async;



import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zju.utils.JedisAdapter;
import com.zju.utils.Rediskeyutil;



@Service
public class EventProducer {
	
	Logger logger = Logger.getLogger(EventProducer.class);
	@Resource
	JedisAdapter jedisAdapter;
	
	public boolean fireEvent(EventModel eventModel) {
		try {
			String eventJson = JSONObject.toJSONString(eventModel);
			String key = Rediskeyutil.getEVentqueueKey();
			jedisAdapter.lpush(key, eventJson);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("插入队列异常"+e.getMessage());
			return false;
		}
	}
}
