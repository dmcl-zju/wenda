package com.zju.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import com.zju.model.Question;
import com.zju.model.User;
import com.zju.model.ViewObject;
import com.zju.service.QuestionService;
import com.zju.service.UserService;


@Controller
public class HomeController {
	
	
	//name指定要实现的实体类
	@Resource(name="userServiceImpl")
	UserService userServiceImpl;
	
	@Resource(name="questionServiceImpl")
	QuestionService questionServiceImpl;
	
	private List<ViewObject> getQustions(int userId,int offset,int limit){
		List<ViewObject> vos = new ArrayList<>();
		List<Question> questions = questionServiceImpl.getLastestQuestions(userId,offset,limit);
		
		for(Question q:questions) {
			ViewObject vo = new ViewObject();
			vo.set("question",q);
			vo.set("user", userServiceImpl.getUserById(q.getUserId()));
			vos.add(vo);
			//System.out.println("问题："+q);
			//System.out.println("发布者："+userServiceImpl.getUserById(q.getUserId()));
		}
		return vos;
	}
	
	
	
	/**
	 * 返回所有的问题
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/","/index"})
	//@ResponseBody
	public String index(Model model) {
		
		List<ViewObject> vos = getQustions(0, 0, 10);
		model.addAttribute("vos", vos);
		
		return "index";
	}
	
	/**
	 * 返回个人问题
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/user/{userId}"})
	//@ResponseBody
	public String userIndex(Model model,@PathVariable("userId") int userId) {
		List<ViewObject> vos = getQustions(userId, 0, 10);
		model.addAttribute("vos", vos);
		return "index";
	}
}
