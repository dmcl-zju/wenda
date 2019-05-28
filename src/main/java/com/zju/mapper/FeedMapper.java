package com.zju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zju.model.Feed;

public interface FeedMapper {

	String TABLE_NAME = " feed ";
	String INSERT_FIELDS = " user_id,type,created_date,data ";
	String SELECT_FIELDS = " id,user_id userId,type,created_date createdDate,data ";
	
	//插入feed对象
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values(#{userId},#{type},#{createdDate},#{data})"})
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")/*增加这个注解插入记录后会返回自增长的id*/
	public int insFeed(Feed feed);
	
	//根据id获取--用于push方式
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
	public Feed selFeedByid(int id);
	
	//根据userId查找----用于pull方式
	public List<Feed> selFeedByUserIds(@Param("userIds") List<Integer> userIds,@Param("maxId") int maxId,@Param("count") int count);
}
