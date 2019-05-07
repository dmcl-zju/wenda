package com.zju.model;

import java.util.Date;

public class Question {
	private int id;
	private String title;
	private String Content;
	private Date createdDate;
	private int userId;
	private int commentCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", Content=" + Content + ", createdDate=" + createdDate
				+ ", userId=" + userId + ", commentCount=" + commentCount + "]";
	}
	
	
	
}
