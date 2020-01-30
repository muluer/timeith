package com.timeith.db.conn;

import java.util.List;

import com.timeith.models.NewsHMDL;

public class CRUDTest {

	public static void main(String[] args) {
		
		NewsHMDL newsItem = null;
		long newsId = 46;
		int count = 45;

		try {
			System.out.println("Test news item create..");
			newsItem = CRUDNews.create(count);
			System.out.println("News item created: " + newsItem.toString());
			HibernateUtils.closeSession();

			System.out.println("Test read all news item..");
			List<NewsHMDL> newsList = CRUDNews.readAll();
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtils.closeSession();

			System.out.println("Test update news item..");
			newsItem = CRUDNews.update(newsId);
			System.out.println("News item updated: " + newsItem.toString());
			HibernateUtils.closeSession();

			System.out.println("Test read single news item..");
			newsItem = CRUDNews.readSingle(newsId);
			System.out.println("News item read: " + newsItem.toString());
			HibernateUtils.closeSession();

			System.out.println("Test delete news item..");
			Boolean isDeleted = CRUDNews.delete(newsId);
			if (isDeleted)
				System.out.println("News item deleted: " + newsId + " deleted..");
			HibernateUtils.closeSession();

			System.out.println("Test read all news item..");
			CRUDNews.readAll();
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtils.closeSession();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		HibernateUtils.shutdown();
	}
}
