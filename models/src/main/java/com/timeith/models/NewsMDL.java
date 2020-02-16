package com.timeith.models;

import java.util.Date;

public class NewsMDL {
	private long newsId;
	private String title;
	private String description;
	private Date publishDate;
	
	public NewsMDL() {
	}
	public NewsMDL(long newsId, String title, String description, Date publishDate) {
		this.newsId = newsId;
		this.title = title;
		this.description = description;
		this.publishDate = publishDate;
	}
	public long getNewsId() {
		return newsId;
	}
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}
