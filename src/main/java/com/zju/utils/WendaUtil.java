package com.zju.utils;

import java.security.MessageDigest;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;


//工具类
public class WendaUtil {
	private static final Logger logger = Logger.getLogger(WendaUtil.class);
	//定义一些常用的常量
	public static int ENTITY_QUESTION = 1;
	public static int SYSTEM_USERID = 1;
	
	
	
	//用户下载图片
	public static String TOUTIAO_DOMAIN = "http://127.0.0.1/";
	//本地存储图像的地址
	public static String IMAGE_DIR = "D:/upload/";
	//常用图片后缀
	public static String[] IMAGE_FILE_EXTD = {"png", "bmp", "jpg", "jpeg"};
	
	//验证图片后缀
	public static boolean isImage(String fileName) {
		for(String ext:IMAGE_FILE_EXTD) {
			if(ext.equals(fileName)) {
				return true;
			}
		}
		return false;
	}
	
	
	//加入状态码
	public static String getJSONString(int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}
	//普通字符创信息
	public static String getJSONString(int code,String msg) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}
	//加入map
	public static String getJSONString(int code,Map<String,Object> map) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		//遍历map，将内容加入json串中
		for(Map.Entry<String, Object> entry:map.entrySet()) {
			json.put(entry.getKey(), entry.getValue());
		}
		return json.toJSONString();
	}
	
	
	//MD5加密
	 public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }
	
}
