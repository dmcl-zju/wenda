package com.zju.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.zju.mapper.QuestionMapper;
import com.zju.model.Question;
import com.zju.service.QuestionService;

@Service()
public class QuestionServiceImpl implements QuestionService {

	@Resource
	QuestionMapper questionMapper;
	
	@Resource
	SensitiveServiceImpl sensitiveServiceImpl;
	
	@Override
	public List<Question> getLastestQuestions(int userId, int offset, int limit) {
		// TODO Auto-generated method stub
		return questionMapper.selLastestQuestions(userId, offset, limit);
	}

	@Override
	public int addQuestion(Question question) {
		
		//html¹ýÂË
		question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
		question.setContent(HtmlUtils.htmlEscape(question.getContent()));
		//Ãô¸Ð´Ê¹ýÂË
		question.setTitle(sensitiveServiceImpl.filter(question.getTitle()));
		question.setContent(sensitiveServiceImpl.filter(question.getContent()));
		
		return questionMapper.insQuestion(question);
	}

	@Override
	public Question getQuestionDetail(int id) {
		return questionMapper.selByid(id);
	}

	@Override
	public int updateQuestionCount(int id,int commentCount) {
		// TODO Auto-generated method stub
		return questionMapper.updCommentCount(id, commentCount);
	}

}
