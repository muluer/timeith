package com.timeith.rss;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.timeith.client.TimeithClient;
import com.timeith.models.NewsHMDL;

public class RSSListenerAA{

	private static final Logger LOGGER = LoggerFactory.getLogger(RSSListenerAA.class);  
	private static final String AARSS_URI 		= "https://www.aa.com.tr/tr/rss/default?cat=guncel";
	private static final String NEWSDB_URI 		= "http://127.0.0.1:9090/webapi/v1/";

	@SuppressWarnings({ "unused"})
	public static void main(String[] args) {
		
		LOGGER.debug("RSS Listener started..");
		
		InputStream contentBody = TimeithClient.getRSS(AARSS_URI);
		SyndFeedInput syndFeedInput = new SyndFeedInput();
		SyndFeed syndFeed = null;
		List<String> titleList = new ArrayList<String>();
		try {
			syndFeed = syndFeedInput.build(new XmlReader(contentBody));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//extract titles
		if (syndFeed == null)
			LOGGER.debug("no content");
		else {
			titleList = syndFeed
					.getEntries()
					.stream()
					.map(entry -> entry.getTitle())
					.collect(Collectors.toList());
		}
		if (titleList.isEmpty())
			LOGGER.debug("title list empty..");
		else {
			titleList.forEach(title -> System.out.println(title));
		}
		
		//transform RSS feeds
		List<NewsHMDL> newsHMDList = syndFeed
				.getEntries()
				.stream()
				.map(feed -> { 
					NewsHMDL newsItem = new NewsHMDL();
					newsItem.setNewsId(-1);
					newsItem.setTitle(feed.getTitle());
					newsItem.setDescription(feed.getDescription().getValue());	
					newsItem.setPublishDate(feed.getPublishedDate());
					return newsItem;
				})
				.collect(Collectors.toList());
		
		//write to db
		List<NewsHMDL> CreatedNewsItems = newsHMDList
				.stream()
				.map(item -> {
					NewsHMDL newsItem = new NewsHMDL(-1, "", "", Date.from(Instant.now()));
					try {
						newsItem = TimeithClient.createRssItem(NEWSDB_URI, item);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return newsItem;
				})
				.collect(Collectors.toList());
		return;
	}
	
}
