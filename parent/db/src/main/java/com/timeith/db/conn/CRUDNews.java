package com.timeith.db.conn;

import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.timeith.models.NewsHMDL;

@SuppressWarnings("unchecked")
public class CRUDNews {

	public static void create(Session session, int count) throws Exception {
		NewsHMDL newsItem = new NewsHMDL();
		newsItem.setTitle("test title" + count);
		newsItem.setDescription("test description" + count);
		newsItem.setPublishDate(ZonedDateTime.now());
		long newsId = -1L;
		newsId = (long) session.save(newsItem);
		if (newsId == -1)
			throw new Exception("Create news item failed..");
		System.out.println(newsId + " created..");
	}

	public static void readAll(Session session) throws Exception {
		String sql = "SELECT n FROM " + NewsHMDL.class.getName() + " n " + "ORDER BY n.newsId";
		Query<NewsHMDL> query = null;
		query = session.createQuery(sql);
		List<NewsHMDL> newsList = query.getResultList();
		if (newsList.isEmpty())
			throw new Exception("News Table is empty..");
		for (NewsHMDL newsHMDL : newsList) {
			System.out.println(newsHMDL.getNewsId() + "\t" + newsHMDL.getTitle() + "\t" + newsHMDL.getDescription()
					+ "\t" + newsHMDL.getPublishDate());
		}
	}

	public static void update(Session session, long newsId) throws Exception {
		NewsHMDL newsItem = null;
		newsItem = session.get(NewsHMDL.class, newsId);
		if (newsItem == null)
			throw new Exception("News Id not found for Update..");
		newsItem.setPublishDate(ZonedDateTime.now());
		session.update(newsItem);
		System.out.println("News item Updated: " + newsItem.toString());
	}

	public static void delete(Session session, long newsId) throws Exception {
		NewsHMDL newsItem = null;
		newsItem = session.get(NewsHMDL.class, newsId);
		if (newsItem == null)
			throw new Exception("News Id not found to delete..");
		session.delete(newsItem);
		System.out.println("News item deleted: " + newsItem.toString());
	}

}
