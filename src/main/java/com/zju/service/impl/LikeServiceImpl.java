package com.zju.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zju.service.LikeService;
import com.zju.utils.JedisAdapter;
import com.zju.utils.Rediskeyutil;

@Service
public class LikeServiceImpl implements LikeService{
   
	@Resource
	JedisAdapter jedisAdapter;
	
	
	
	@Override
	public long like(int userId, int entityType, int entityId) {
		// TODO Auto-generated method stub
		String likeKey = Rediskeyutil.getLikeKey(entityType, entityId);
		jedisAdapter.sadd(likeKey, String.valueOf(userId));
		
		String dislikeKey = Rediskeyutil.getDislikeKey(entityType, entityId);
		jedisAdapter.srem(dislikeKey, String.valueOf(userId));
		
		return jedisAdapter.scard(likeKey);
	}

	@Override
	public long dislike(int userId, int entityType, int entityId) {
		// TODO Auto-generated method stub
		String likeKey = Rediskeyutil.getLikeKey(entityType, entityId);
		jedisAdapter.srem(likeKey, String.valueOf(userId));
		
		String dislikeKey = Rediskeyutil.getDislikeKey(entityType, entityId);
		jedisAdapter.sadd(dislikeKey, String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}

	@Override
	public int getLikestatus(int userId, int entityType, int entityId) {
		// TODO Auto-generated method stub
		String likeKey = Rediskeyutil.getLikeKey(entityType, entityId);
		String dislikeKey = Rediskeyutil.getDislikeKey(entityType, entityId);
		if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}
		if(jedisAdapter.sismember(dislikeKey, String.valueOf(userId))) {
			return -1;
		}
		return 0;
	}

	@Override
	public long getLikecount(int entityType, int entityId) {
		// TODO Auto-generated method stub
		String likeKey = Rediskeyutil.getLikeKey(entityType, entityId);
		return jedisAdapter.scard(likeKey);
	}

}
