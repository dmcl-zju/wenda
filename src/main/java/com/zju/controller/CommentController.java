package com.zju.controller;


import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zju.async.EventModel;
import com.zju.async.EventProducer;
import com.zju.async.EventType;
import com.zju.model.Comment;
import com.zju.model.EntityType;
import com.zju.model.HostHolder;
import com.zju.model.Question;
import com.zju.service.CommentService;
import com.zju.service.QuestionService;




@Controller
public class CommentController {
	
	Logger logger = Logger.getLogger(CommentController.class);
	
	@Resource
	CommentService commentServiceImpl;
	
	@Resource
	HostHolder hostHolder;
	
	@Resource
	QuestionService questionServiceImpl;
	
	@Resource
	EventProducer eventProduce;
	
	
	
	@RequestMapping(value = {"/addComment"}, method = {RequestMethod.POST})
	public String addComment(@RequestParam("questionId") int questionId,
							 @RequestParam("content") String content) {
		try {
			Comment comment = new Comment();
			
			//判断是不是登陆用户
			if(hostHolder.get()!=null) {
				comment.setUserId(hostHolder.get().getId());
			}else {
				//如果不是登陆用户直接让其去登陆
				return "redirect:/reglogin";
			}
			comment.setContent(content);
			comment.setCreatedDate(new Date());
			comment.setEntityId(questionId);
			comment.setEntityType(EntityType.ENTITY_QUESTION);
			comment.setStatus(0);
			//业务层提交
			commentServiceImpl.addComment(comment);
			
			//触发异步事件
			EventModel eventModel = new EventModel();
			Question question = questionServiceImpl.getQuestionDetail(questionId);
			eventModel.setActorId(hostHolder.get().getId()).setEntityOwnerId(question.getUserId()).setEntityType(EntityType.ENTITY_QUESTION)
						.setEntityId(questionId).setEventType(EventType.COMMENT);
			eventProduce.fireEvent(eventModel);
			
			//更新评论的数量--之后考虑异步化更新
			int commentCount = commentServiceImpl.getCommentCount(comment.getEntityId(), comment.getEntityType());
			questionServiceImpl.updateQuestionCount(comment.getEntityId(), commentCount);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("提交评论出错"+e.getMessage());
		}
		//重定向到群体详情页面
		return "redirect:/question/" + String.valueOf(questionId);
	}
}
