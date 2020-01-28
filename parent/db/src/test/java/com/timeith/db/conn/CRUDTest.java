package com.timeith.db.conn;

import java.util.List;

import org.hibernate.Session;

import com.timeith.models.NewsHMDL;

public class CRUDTest {

	private static Session session = null;

	public static void main(String[] args) {
		
		NewsHMDL newsItem = null;
		long newsId = 46;
		int count = 45;

		try {
			session = HibernateUtils.getSession();
			System.out.println("Test news item create..");
			newsItem = CRUDNews.create(count);
			System.out.println("News item created: " + newsItem.toString());
			HibernateUtils.closeSession();

			session = HibernateUtils.getSession();
			System.out.println("Test read all news item..");
			List<NewsHMDL> newsList = CRUDNews.readAll(session);
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtils.closeSession();

			session = HibernateUtils.getSession();
			System.out.println("Test update news item..");
			newsItem = CRUDNews.update(session, newsId);
			System.out.println("News item updated: " + newsItem.toString());
			HibernateUtils.closeSession();

			session = HibernateUtils.getSession();
			System.out.println("Test read single news item..");
			newsItem = CRUDNews.readSingle(session, newsId);
			System.out.println("News item read: " + newsItem.toString());
			HibernateUtils.closeSession();

			session = HibernateUtils.getSession();
			System.out.println("Test delete news item..");
			Boolean isDeleted = CRUDNews.delete(session, newsId);
			if (isDeleted)
				System.out.println("News item deleted: " + newsId + " deleted..");
			HibernateUtils.closeSession();

			session = HibernateUtils.getSession();
			System.out.println("Test read all news item..");
			CRUDNews.readAll(session);
			for (NewsHMDL newsHMDL : newsList)
				System.out.println(newsHMDL.toString());
			HibernateUtils.closeSession();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		HibernateUtils.shutdown();
	}
}
