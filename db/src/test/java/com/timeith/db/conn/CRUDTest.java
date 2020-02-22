package com.timeith.db.conn;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.timeith.db.utils.HibernateUtilsDB;
import com.timeith.models.NewsHMDL;

public class CRUDTest {

	public static void main(String[] args) {
		
		NewsHMDL newsItem = null;
		long newsId = 46;

		try {
			System.out.println("Test news item create..");
			newsItem = new NewsHMDL(-1, "title001", "description001", Date.from(Instant.now()));
			newsItem = CRUDNewsDB.create(newsItem);
			System.out.println("News item created: " + newsItem.toString());
			HibernateUtilsDB.closeSession();

			System.out.println("Test read all news item..");
			List<NewsHMDL> newsList = CRUDNewsDB.readAll();
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtilsDB.closeSession();

			System.out.println("Test update news item..");
			NewsHMDL tmpNewsItem = new NewsHMDL(1, "title1001", "description1001", Date.from(Instant.now()));
			newsItem = CRUDNewsDB.update(tmpNewsItem);
			System.out.println("News item updated: " + newsItem.toString());
			HibernateUtilsDB.closeSession();

			System.out.println("Test read single news item..");
			newsItem = CRUDNewsDB.readSingle(newsId);
			System.out.println("News item read: " + newsItem.toString());
			HibernateUtilsDB.closeSession();

			System.out.println("Test delete news item..");
			Boolean isDeleted = CRUDNewsDB.delete(newsId);
			if (isDeleted)
				System.out.println("News item deleted: " + newsId + " deleted..");
			HibernateUtilsDB.closeSession();

			System.out.println("Test read all news item..");
			CRUDNewsDB.readAll();
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtilsDB.closeSession();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		HibernateUtilsDB.shutdown();
	}
}
