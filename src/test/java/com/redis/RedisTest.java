package com.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	
	@SuppressWarnings("resource")
	@Test
	public void test() {
		
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("--"+jedis.ping());
	}
	
	@Test
	public void demo1(){
		Jedis jedis=new Jedis("192.168.254.128",6379);//ip��ַ���˿ں�
		jedis.auth("123456");
		jedis.set("name","hand");
		//3.��ȡ����
		String value1=jedis.get("name");
		System.out.println(value1);
		jedis.close();
	}

	@Test
	public void demo2(){
	
		String s="jhjpopo,uii";
		System.out.println("==="+s.substring(3));
		System.out.println("--"+s.lastIndexOf(","));
		
	}
	
	

}
