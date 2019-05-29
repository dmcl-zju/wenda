package com.zju.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zju.model.EntityType;
import com.zju.model.Question;
import com.zju.model.ViewObject;
import com.zju.service.FollowService;
import com.zju.service.QuestionService;
import com.zju.service.UserService;
import com.zju.service.impl.SearchServiceImpl;

@Controller
public class SearchController {
	
	Logger logger = Logger.getLogger(SearchController.class);
	
	@Resource
	SearchServiceImpl searchServiceImpl;
	
	@Resource 
	QuestionService questionServiceImpl;
	
	@Resource
	FollowService followServiceImpl; 
	
	@Resource
	UserService userServiceImpl;
	
	@RequestMapping({"/search"})
	public String search(Model model,@RequestParam("q") String keyword,
						 @RequestParam(value="offset",defaultValue="0") int offset,
						 @RequestParam(value="count",defaultValue="10") int count) {
		
		try {
			List<Question> questions = searchServiceImpl.searchQuestion(keyword, offset, count, "<em>","</em>");
			List<ViewObject> vos = new ArrayList<>();
			for(Question question:questions) {
				ViewObject vo = new ViewObject();
				Question q = questionServiceImpl.getQuestionDetail(question.getId());
				if(question.getContent()!=null) {
					q.setContent(question.getContent());
				}
				if(question.getTitle()!=null) {
					q.setTitle(question.getTitle());
				}
				vo.set("question",q);
				vo.set("followCount",followServiceImpl.getFollowerCount(EntityType.ENTITY_QUESTION,q.getId()));
				vo.set("user", userServiceImpl.getUserById(q.getUserId()));
				vos.add(vo);
			}
			model.addAttribute("vos",vos);
			model.addAttribute("keyword",keyword);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ËÑË÷³ö´í"+e.getMessage());
		}					
		return "result";	
	}
}
