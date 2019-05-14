package com.zju.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.zju.mapper.CommentMapper;
import com.zju.model.Comment;
import com.zju.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Resource
	CommentMapper commentMapper;
	
	@Resource
	SensitiveServiceImpl sensitiveServiceImpl; 
	
	
	@Override
	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
		//先进行敏感词过滤等操作
		//html过滤
		comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
		//敏感词过滤
		comment.setContent(sensitiveServiceImpl.filter(comment.getContent()));
		return commentMapper.insComment(comment);
	}

	@Override
	public List<Comment> getCommentByEntity(int entityId, int entityType) {
		// TODO Auto-generated method stub
		return commentMapper.selByEntity(entityId, entityType);
	}

	@Override
	public List<Comment> getCommentByUserId(int userId) {
		// TODO Auto-generated method stub
		return commentMapper.selByUserId(userId);
	}

	@Override
	public int deleteComment(int entityId, int entityType) {
		// TODO Auto-generated method stub
		return commentMapper.updCommentStatus(entityId, entityType, 1);
	}

	@Override
	public int getCommentCount(int entityId, int entityType) {
		// TODO Auto-generated method stub
		return commentMapper.selCommentCount(entityId, entityType);
	}

}
