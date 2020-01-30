package com.timeith.models;

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
public class RSSLinksHMDL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rsslinkid")
	private long rssLinkId;
	@Column(name = "rsslinkcategory")
	private String rsslinkcategory;
	@Column(name = "rsslinkdescription")
	private String rsslinkdescription;
	@Column(name = "rsslinkurl")
	private String rsslinkurl;

	public RSSLinksHMDL() {
		super();
	}

	public RSSLinksHMDL(long rssLinkId, String rsslinkcategory, String rsslinkdescription, String rsslinkurl) {
		super();
		this.rssLinkId = rssLinkId;
		this.rsslinkcategory = rsslinkcategory;
		this.rsslinkdescription = rsslinkdescription;
		this.rsslinkurl = rsslinkurl;
	}

	public long getRssLinkId() {
		return rssLinkId;
	}

	public void setRssLinkId(long rssLinkId) {
		this.rssLinkId = rssLinkId;
	}

	public String getRsslinkcategory() {
		return rsslinkcategory;
	}

	public void setRsslinkcategory(String rsslinkcategory) {
		this.rsslinkcategory = rsslinkcategory;
	}

	public String getRsslinkdescription() {
		return rsslinkdescription;
	}

	public void setRsslinkdescription(String rsslinkdescription) {
		this.rsslinkdescription = rsslinkdescription;
	}

	public String getRsslinkurl() {
		return rsslinkurl;
	}

	public void setRsslinkurl(String rsslinkurl) {
		this.rsslinkurl = rsslinkurl;
	}

	@Override
	public String toString() {
		return "RSSLinksHMDL [rssLinkId=" + rssLinkId + ", rsslinkcategory=" + rsslinkcategory + ", rsslinkdescription="
				+ rsslinkdescription + ", rsslinkurl=" + rsslinkurl + "]";
	}
	
}
