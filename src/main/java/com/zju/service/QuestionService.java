package com.zju.service;

import java.util.List;

import com.zju.model.Question;

public interface QuestionService {
	
	public List<Question> getLastestQuestions(int userId,int offset,int limit);
}
