package com.timeith.rss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.timeith.client.TimeithClientRSS;
import com.timeith.models.NewsHMDL;

public class RSSListenerAA{

	private static final Logger LOGGER = LoggerFactory.getLogger(RSSListenerAA.class);  
	private static final String CONTENT_URI = "https://www.aa.com.tr/tr/rss/default?cat=guncel";

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		

		LOGGER.debug("RSS Listener started..");
		
		TimeithClientRSS timeithClientRSS = new TimeithClientRSS();
		InputStream contentBody = timeithClientRSS.getRSS(CONTENT_URI);
		timeithClientRSS.close();
		
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
		
		//write RSS to DB
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
	return;
	}
	
}
