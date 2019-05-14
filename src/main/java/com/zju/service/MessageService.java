package com.zju.service;

import java.util.List;

import com.zju.model.Message;

public interface MessageService {
	
	public int addMessage(Message message);
	
	public List<Message> getConversationDetail(String conversationId,int limit,int offset);
	
	public List<Message> getConversationList(int userId,int offset,int limit);
	
	public int getUnreadCount(int userId,String conversationId);
	
	public int clearUnreadCount(int userId,String conversationId);
}
