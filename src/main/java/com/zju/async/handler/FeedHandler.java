package com.zju.async.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zju.async.EventHandler;
import com.zju.async.EventModel;
import com.zju.async.EventType;
import com.zju.model.EntityType;
import com.zju.model.Feed;
import com.zju.model.HostHolder;
import com.zju.model.Question;
import com.zju.model.User;
import com.zju.service.FeedService;
import com.zju.service.FollowService;
import com.zju.service.QuestionService;
import com.zju.service.UserService;
import com.zju.utils.JedisAdapter;
import com.zju.utils.Rediskeyutil;


@Component
public class FeedHandler implements EventHandler {
	
	@Resource
	FeedService feedServiceImpl;
	
	@Resource
	QuestionService questionServiceImpl;
	
	@Resource
	UserService userServiceImpl;
	
	@Resource
	FollowService followServiceImpl;
	
	@Resource
	JedisAdapter jedisAdapter;

	//从model中获得事件信息，将其转化成json串
	private String getFeedData(EventModel model) {
		Map<String,String> map = new HashMap<>();
		User user = userServiceImpl.getUserById(model.getActorId());
		if(null==user) {
			return null;
		}
		map.put("userId",String.valueOf(user.getId()));
		map.put("userHead",user.getHeadUrl());
		map.put("userName", user.getName());
		
		
		//两种情况下需要获取问题对象：回答了某个问题 或者 关注了某个问题
		if(model.getEventType()==EventType.COMMENT || (model.getEventType()==EventType.FOLLOW&&model.getEntityType()==EntityType.ENTITY_QUESTION)) {
			Question q = questionServiceImpl.getQuestionDetail(model.getEntityId());
			if(null==q) {
				return null;
			}
			map.put("questionId", String.valueOf(q.getId()));
			map.put("questionTitle", q.getTitle());
			return JSONObject.toJSONString(map);
		}
		return null;
	}
	
	@Override
	public void doHandler(EventModel model) {
		// TODO Auto-generated method stub
		
		//System.out.println("进入了FeedHandler");
		//添加feed到数据库
		Feed feed = new Feed();
		feed.setUserId(model.getActorId());
		feed.setType(model.getEventType().getValue());
		feed.setCreatedDate(new Date());
		//如果feed不合法
		if(null == getFeedData(model)) {
			return;
		}
		//System.out.println("我的模型信息："+getFeedData(model));
		feed.setData(getFeedData(model));
		feedServiceImpl.addFeed(feed);
		
		//一下为push方案需要做的
		//获取自己的粉丝id
		List<Integer> followers = followServiceImpl.getFollowers(EntityType.ENTITY_USER,model.getActorId(),0,Integer.MAX_VALUE);
		followers.add(0);
		for(int follower:followers) {
			String timelineKey = Rediskeyutil.getTimelineKey(follower);
			jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
		}
		
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		// TODO Auto-generated method stub
		List<EventType> types = new ArrayList<>();
		types.add(EventType.COMMENT);
		types.add(EventType.FOLLOW);
		return types;
	}

}
