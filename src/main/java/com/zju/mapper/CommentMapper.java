package com.zju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zju.model.Comment;

public interface CommentMapper {
	
	String TABLE_NAME = " comment ";
	String INSERT_FIELDS = " content,user_id, entity_id,entity_type,created_date,status ";
	String SELECT_FIELDS = " id,content,user_id userId, entity_id entityId,entity_type entityType,created_date createdDate,status ";
	
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values(#{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
	public int insComment(Comment comment);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType}"})
	public List<Comment> selByEntity(@Param("entityId") int entityId,
									 @Param("entityType") int entityType);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where user_id=#{userId}"})
	public List<Comment> selByUserId(int userId);
	
	@Select({"select count(id) from", TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType}"})
	public int selCommentCount(@Param("entityId") int entityId,
							   @Param("entityType") int entityType);
	
	@Update({"update",TABLE_NAME,"set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
	public int updCommentStatus(@Param("entityId") int entityId,
							   @Param("entityType") int entityType,
							   @Param("status") int status);
	
}
