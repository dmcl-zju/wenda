package com.zju.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zju.model.HostHolder;
import com.zju.model.Question;
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
			Question q = questionServiceImpl.getQuestionDetail(qid);
			model.addAttribute("question", q);
			model.addAttribute("user", userServiceImpl.getUserById(q.getUserId()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("获取信息错误"+e.getMessage());
	    }
		return "detail";
	}

}
