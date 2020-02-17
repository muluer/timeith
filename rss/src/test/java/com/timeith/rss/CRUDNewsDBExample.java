package com.timeith.rss;

import java.time.Instant;
import java.util.Date;

import com.timeith.client.TimeithClient;
import com.timeith.models.NewsHMDL;

public class CRUDNewsDBExample {

	private static final String NEWSDB_URI 		= "http://127.0.0.1:9090/webapi/v1/";

	public static void main(String[] args) {
		long newsId = 257;

		//read from to db
		NewsHMDL newsItem = null;
		try {
			newsItem = TimeithClient.readRssItem(NEWSDB_URI , newsId);
			System.out.println(newsItem.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NewsHMDL tmpNewsItem = new NewsHMDL(-1, "test003", "testdesc003", Date.from(Instant.now()));
		newsItem = new NewsHMDL(-1, "", "", Date.from(Instant.now()));
		try {
			newsItem = TimeithClient.createRssItem(NEWSDB_URI, tmpNewsItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (newsItem.getNewsId() == -1) 
			System.out.println("news item not created..");
		else 
			System.out.println(newsItem.toString());
		return;
	}

}
