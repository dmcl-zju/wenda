package com.zju.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zju.model.EntityType;
import com.zju.service.LikeService;
import com.zju.utils.JedisAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件的位子
@ContextConfiguration("classpath:/applicationContext.xml")
public class JedisTest {
	
	@Resource
	JedisAdapter jedisAdapter;
	
	@Resource
	LikeService likeServiceImpl;
	
	@Test
	public void testJedis() {
		/*String keyName = "table1";
		jedisAdapter.sadd(keyName, "1");
		jedisAdapter.sadd(keyName, "2");
		jedisAdapter.sadd(keyName, "3");
		jedisAdapter.sadd(keyName, "4");
		jedisAdapter.sadd(keyName, "1");
		
		System.out.println(jedisAdapter.scard(keyName));
		System.out.println(jedisAdapter.sismember(keyName, "3"));
		jedisAdapter.srem(keyName, "3");
		
		System.out.println(jedisAdapter.scard(keyName));
		System.out.println(jedisAdapter.sismember(keyName, "3"));*/
		
		
		System.out.println(likeServiceImpl.like(200, EntityType.ENTITY_COMMENT, 100));
		
		
		
	}
}
