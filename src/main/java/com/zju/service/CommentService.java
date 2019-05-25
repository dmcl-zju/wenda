package com.zju.service;

import java.util.List;

import com.zju.model.Comment;

public interface CommentService {
	
	//增加评论
	public int addComment(Comment comment);
	//根据id获取评论对象
	public Comment getCommentById(int commentId);
	//获取某个问题的所有评论
	public List<Comment> getCommentByEntity(int entityId,int entityType);
	//获取某个用户的评论
	public List<Comment> getCommentByUserId(int userId); 
	//删除用户评论
	public int deleteComment(int entityId,int entityType);
	//查看评论数
	public int getCommentCount(int entityId,int entityType);
	
}
