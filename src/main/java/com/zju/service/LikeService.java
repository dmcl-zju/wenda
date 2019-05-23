package com.zju.service;

public interface LikeService {
	
	public long like(int userId,int entityType,int entityId);
	
	public long dislike(int userId,int entityType,int entityId);
	
	public int getLikestatus(int userId,int entityType,int entityId);
	
	public long getLikecount(int entityType,int entityId);
}
