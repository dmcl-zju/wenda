package com.zju.utils;

public class Rediskeyutil {
	
	
	private static String SPLIT = ":";
	private static String LIKE = "LIKE";
	private static String DISLIKE = "DISLIKE";
	

	public static String getLikeKey(int entityType,int entityId) {
		return LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
	}
	
	public static String getDislikeKey(int entityType,int entityId) {
		return DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
	}
}
