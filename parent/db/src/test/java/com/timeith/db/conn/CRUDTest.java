package com.timeith.db.conn;

import java.util.List;

import com.timeith.db.utils.HibernateUtilsDB;
import com.timeith.models.NewsHMDL;

public class CRUDTest {

	public static void main(String[] args) {
		
		NewsHMDL newsItem = null;
		long newsId = 46;
		int count = 45;

		try {
			System.out.println("Test news item create..");
			newsItem = CRUDNewsDB.create(count);
			System.out.println("News item created: " + newsItem.toString());
			HibernateUtilsDB.closeSession();

			System.out.println("Test read all news item..");
			List<NewsHMDL> newsList = CRUDNewsDB.readAll();
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtilsDB.closeSession();

			System.out.println("Test update news item..");
			newsItem = CRUDNewsDB.update(newsId);
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
