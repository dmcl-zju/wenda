package com.zju.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zju.model.Comment;
import com.zju.model.EntityType;
import com.zju.model.HostHolder;
import com.zju.model.Question;
import com.zju.model.ViewObject;
import com.zju.service.CommentService;
import com.zju.service.LikeService;
import com.zju.service.QuestionService;
import com.zju.service.UserService;
import com.zju.utils.WendaUtil;

/**
 * 问题发布
 * @author lin
 *
 */

@Controller
public class QuestionController {
	
	Logger logger = Logger.getLogger(QuestionController.class);

	@Resource(name="questionServiceImpl")
	QuestionService questionServiceImpl;
	
	@Resource
	HostHolder hostHolder;

	@Resource
	UserService userServiceImpl;
	
	@Resource 
	CommentService commentServiceImpl;
	
	@Resource
	LikeService likeServiceImpl;
	
	
	@RequestMapping(value="/question/add")
	@ResponseBody
	public String addQuestion(@RequestParam("title") String title,
							  @RequestParam("content") String content) {
		
		try {
			if(hostHolder.get() == null) {
				return WendaUtil.getJSONString(999);
			}
			Question q = new Question();
			q.setTitle(title);
			q.setContent(content);
			q.setCommentCount(0);
			q.setCreatedDate(new Date());
			q.setUserId(hostHolder.get().getId());
			questionServiceImpl.addQuestion(q);
			return WendaUtil.getJSONString(0);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("添加错误"+e.getMessage());
			return WendaUtil.getJSONString(1,"失败");
		}
	}
	
	@RequestMapping(value="/question/{qid}")
	public String questionDetail(Model model,@PathVariable("qid") int qid) {
		
		try {
			//获取问题本身
			Question q = questionServiceImpl.getQuestionDetail(qid);
			model.addAttribute("question", q);
			//获取问题的评论
			List<Comment> comments = commentServiceImpl.getCommentByEntity(qid, EntityType.ENTITY_QUESTION);
			List<ViewObject> vos = new ArrayList<>();
			for(Comment comment:comments) {
				ViewObject vo = new ViewObject();
				//增加赞和踩功能
				if(hostHolder.get()==null) {
					vo.set("liked", 0);
				}else {
					vo.set("liked", likeServiceImpl.getLikestatus(hostHolder.get().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
				}
				vo.set("likeCount", likeServiceImpl.getLikecount(EntityType.ENTITY_COMMENT, comment.getId()));
				vo.set("comment", comment);
				vo.set("user", userServiceImpl.getUserById(comment.getUserId()));
				vos.add(vo);
			}
			model.addAttribute("comments", vos);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("获取信息错误"+e.getMessage());
	    }
		return "detail";
	}

}
