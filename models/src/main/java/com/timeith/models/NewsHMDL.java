package com.timeith.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "news")
@XmlRootElement
public class NewsHMDL implements Serializable{

	private static final long serialVersionUID = -9193154491975586526L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "newsId")
	private long newsId;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "publishDate")
	private Date publishDate;
	@Column(name = "guId")
	private String guId;	
	@Column(name = "link")
	private String link;
	@Column(name = "image")
	private String image;

	public NewsHMDL() {
	}

	public NewsHMDL(long newsId, String title, String description, Date publishDate) {
		this.newsId = newsId;
		this.title = title;
		this.description = description;
		this.publishDate = publishDate;
	}

	public NewsHMDL(long newsId, String title, String description, Date publishDate, String guId, String link,
			String image) {
		this.newsId = newsId;
		this.title = title;
		this.description = description;
		this.publishDate = publishDate;
		this.guId = guId;
		this.link = link;
		this.image = image;
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

	public String getGuId() {
		return guId;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "NewsHMDL [newsId=" + newsId + ", guId=" + guId + ", title=" + title + ", link=" + link + ", image="
				+ image + ", description=" + description + ", publishDate=" + publishDate + "]";
	}

}
