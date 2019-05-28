package com.zju.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zju.model.EntityType;
import com.zju.model.Feed;
import com.zju.model.HostHolder;
import com.zju.service.FeedService;
import com.zju.service.FollowService;
import com.zju.utils.JedisAdapter;
import com.zju.utils.Rediskeyutil;

@Controller
public class FeedController {
	
	@Resource
	HostHolder hostHolder;
	
	@Resource
	FollowService followServiceImpl;
	
	@Resource
	FeedService feedServiceImpl;
	
	@Resource
	JedisAdapter jedisAdapter;
	
	
	@RequestMapping({"pullfeeds"})
	public String pullFeeds(Model model) {
		
		int hostUserId = hostHolder.get()==null?0:hostHolder.get().getId();
		List<Integer> followees = new ArrayList<>();
		//找到自己关注的用户--先拿10个用户的
		if(hostUserId!=0) {
			followees = followServiceImpl.getFollowees(EntityType.ENTITY_USER, hostUserId, 0, 10);
		}
		List<Feed> feeds = feedServiceImpl.getFeedsByUsers(followees,Integer.MAX_VALUE,10);

		model.addAttribute("feeds",feeds);
		return "feeds";
	}
	
	@RequestMapping({"pushfeeds"})
	public String pushFeeds(Model model) {
		int hostUserId = hostHolder.get()==null?0:hostHolder.get().getId();
		//把自己的timeline从redis中取出来
		String timelineKey = Rediskeyutil.getTimelineKey(hostUserId);
		List<String> timelines = jedisAdapter.lrange(timelineKey,0,10);
		List<Feed> feeds = new ArrayList<>();
		for(String timeline:timelines) {
			Feed feed = feedServiceImpl.getFeedById(Integer.valueOf(timeline));
			if(feed!=null) {
				feeds.add(feed);
			}
		}
		model.addAttribute("feeds",feeds);
		return "feeds";
	}
}
