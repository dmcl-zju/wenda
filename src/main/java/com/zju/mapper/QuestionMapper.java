package com.zju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zju.model.Question;

public interface QuestionMapper {
	String TABLE_NAME = " question ";
	String INSERT_FIELDS = " title,content,created_date,user_id,comment_count ";
	//这里和实体类名和数据库中不一致可以使用别名来实现 如：head_url headurl，虽然实体类中为headUrl但是这里不会区分大小写
	String SELECT_FIELDS = " id,title,content,created_date createddate,user_id userid,comment_count commentcount";
	
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
				") values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
	int insQuestion(Question question);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME})
	List<Question> selAll();
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
	Question selByid(int id);
	
	//使用xml方式接口绑定方案----对于复杂sql语句的处理时考虑使用
	List<Question> selLastestQuestions(@Param("userId") int userId,
									   @Param("offset") int offset,
									   @Param("limit") int limit);
}
