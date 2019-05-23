package com.zju.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zju.model.EntityType;
import com.zju.model.HostHolder;
import com.zju.service.LikeService;
import com.zju.utils.WendaUtil;

@Controller
public class LikeController {
	@Resource
	LikeService likeServiceImpl;
	@Resource
	HostHolder hostHolder;
	
	@RequestMapping(value = {"/like"}, method = {RequestMethod.POST})
	@ResponseBody
	public String like(@RequestParam("commentId") int commentId) {
		if(hostHolder.get()==null) {
			return WendaUtil.getJSONString(999);
		}
		long likecount = likeServiceImpl.like(hostHolder.get().getId(),EntityType.ENTITY_COMMENT, commentId);
		return WendaUtil.getJSONString(0,String.valueOf(likecount));
	}
	
	@RequestMapping(value = {"/dislike"}, method = {RequestMethod.POST})
	@ResponseBody
	public String dislike(@RequestParam("commentId") int commentId) {
		if(hostHolder.get()==null) {
			return WendaUtil.getJSONString(999);
		}
		long likecount = likeServiceImpl.dislike(hostHolder.get().getId(),EntityType.ENTITY_COMMENT, commentId);
		return WendaUtil.getJSONString(0,String.valueOf(likecount));
	}
}
