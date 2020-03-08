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
import com.timeith.commons.util.MapperWrapper;
import com.timeith.models.NewsHMDL;

public class RSSListenerAA{

	private static final Logger LOGGER = LoggerFactory.getLogger(RSSListenerAA.class);
	private static final String CACHE_DIR = "src/main/resources/cacheDate";
	private static final String AARSS_URI = "https://www.aa.com.tr/tr/rss/default?cat=guncel";
	private static final String NEWSDB_URI = "http://127.0.0.1:9090/webapi/v1/";
	private static final Date cachedDate = getCachedDate(CACHE_DIR);
 

	@SuppressWarnings({ "unused"})
	public static void main(String[] args) {
		
		LOGGER.debug("RSS Listener started..");
		
		InputStream contentBody = TimeithClient.getRSS(AARSS_URI);
		SyndFeedInput syndFeedInput = new SyndFeedInput();
		SyndFeed syndFeed = null;
		try {
			syndFeed = syndFeedInput.build(new XmlReader(contentBody));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// debug
		printTitles(syndFeed);
		printGuids(syndFeed);
		
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
					newsItem.setGuId(feed.getUri());
					newsItem.setLink(feed.getLink());
					newsItem.setImage(feed.getLink());;
					return newsItem;
				})
				.collect(Collectors.toList());
		
		if (newsHMDList.isEmpty()) {
			LOGGER.debug("empty feed..");
			return;
		} else {
			// cache latest date
				List<NewsHMDL> writeToDB = null;
				try {
					writeToDB = itemCache(CACHE_DIR, newsHMDList);
					if (writeToDB.isEmpty())
						return;
					else {
						// write all new items to db
							List<Long> NewsAllItemsWritten = TimeithClient.writeAllRssItem(NEWSDB_URI, writeToDB);
						}	
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
		}
	}

	private static List<NewsHMDL> itemCache(String cacheDir, List<NewsHMDL> newsItemList) throws IOException {
		
		List<NewsHMDL> writeToDB = new ArrayList<NewsHMDL>();

		Date latestDate = null;

		writeToDB = newsItemList
				.stream()
				.filter(item -> {
					if (item.getPublishDate().compareTo(cachedDate) > 0)
						return true;
					else
						return false;
					})
				.collect(Collectors.toList());
		
		if (!writeToDB.isEmpty()) {
			latestDate = writeToDB
					.stream()
					.map(item -> item.getPublishDate())
					.reduce(cachedDate, (d1, d2) -> {
						if (d1.after(d2))
							return d1;
						else
							return d2;
					});
		}
		
		if (latestDate != null) {
			try {
				MapperWrapper.writeWithClassRefence(cacheDir, latestDate);
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return writeToDB;
	}		
	
	private static Date getCachedDate(String cacheDir) {
		boolean cacheFileOK = false;
		Date latestDate = null;
		Date cachedDate = null;
		
		try {
			latestDate = MapperWrapper.readWithClassRefence(cacheDir, Date.class);
			cacheFileOK = true;
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		cachedDate = cacheFileOK ? latestDate : Date.from(Instant.EPOCH);
		return cachedDate;
	}

	private static void printTitles(SyndFeed syndFeed) {
		//extract titles
		List<String> titleList = new ArrayList<String>();
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
	}
	
	private static void printGuids(SyndFeed syndFeed) {
		syndFeed.getEntries().stream().map(item -> item.getUri()).forEach(item -> System.out.println(item));
	}
}
