package com.zju.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zju.mapper.FeedMapper;
import com.zju.model.Feed;
import com.zju.service.FeedService;

@Service
public class FeedServiceImpl implements FeedService{
	
	@Resource
	FeedMapper feedMapper;
	
	@Override
	public boolean addFeed(Feed feed) {
		// TODO Auto-generated method stub
		int res = feedMapper.insFeed(feed);
		return res>0;
	}

	@Override
	public Feed getFeedById(int id) {
		// TODO Auto-generated method stub
		return feedMapper.selFeedByid(id);
	}

	@Override
	public List<Feed> getFeedsByUsers(List<Integer> userIds, int maxId, int count) {
		// TODO Auto-generated method stub
		return feedMapper.selFeedByUserIds(userIds, maxId, count);
	}
}
