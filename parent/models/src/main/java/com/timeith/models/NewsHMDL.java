package com.timeith.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="news")
@XmlRootElement
public class NewsHMDL {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "newsId")
	private long newsId;
	@Column(name="title")
	private String title;
	@Column(name="description")
	private String description;
	@Column(name="publishDate")
	private ZonedDateTime publishDate;

	public NewsHMDL() {
	}

	public NewsHMDL(long newsId, String title, String description, ZonedDateTime publishDate) {
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

	public ZonedDateTime getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(ZonedDateTime publishDate) {
		this.publishDate = publishDate;
	}

	@Override
	public String toString() {
		return "NewsHMDL [newsId=" + newsId + ", title=" + title + ", description=" + description + ", publishDate="
				+ publishDate + "]";
	}
	
}
