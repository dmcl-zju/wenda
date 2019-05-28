package com.zju.service;

import java.util.List;

/**
 * 	关注Service
 * @author lin
 *
 */
public interface FollowService {
	
	
	/**
	 * 
	 * @param userId 谁触发的关注操作---一般就是当前登录用户
	 * @param entityType 要关注的实体对象类型
	 * @param entityId 要关注的实体对象id
	 * @return
	 */
	public boolean follow(int userId,int entityType,int entityId);
	//取消关注
	public boolean unfollow(int userId,int entityType,int entityId);
	//获取关注自己的粉丝
	public List<Integer> getFollowers(int entityType,int entityId,int offset,int count);
	//获取自己关注对象
	public List<Integer> getFollowees(int entityType,int userId,int offset,int count);
	//返回粉丝数目
	public long getFollowerCount(int entityType,int entityId);
	//返回关注数
	public long getFolloweeCount(int entityType,int userId);
	//返回是不是粉丝（关注者）
	public boolean isFollower(int userId,int entityType,int entityId);
	
}
