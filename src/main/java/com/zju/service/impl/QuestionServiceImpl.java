package com.zju.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zju.mapper.QuestionMapper;
import com.zju.model.Question;
import com.zju.service.QuestionService;

@Service()
public class QuestionServiceImpl implements QuestionService {

	@Resource
	QuestionMapper questionMapper;
	
	@Override
	public List<Question> getLastestQuestions(int userId, int offset, int limit) {
		// TODO Auto-generated method stub
		return questionMapper.selLastestQuestions(userId, offset, limit);
	}

}
