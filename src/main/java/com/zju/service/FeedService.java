package com.zju.service;

import java.util.List;

import com.zju.model.Feed;

public interface FeedService {
	
	public boolean addFeed(Feed feed);
	
	public Feed getFeedById(int id);
	
	public List<Feed> getFeedsByUsers(List<Integer> userIds,int maxId,int count);
}
