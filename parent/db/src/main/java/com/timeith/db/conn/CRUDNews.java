package com.timeith.db.conn;

import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.timeith.models.NewsHMDL;

@SuppressWarnings("unchecked")
public class CRUDNews {

	public static NewsHMDL create(int count) throws Exception {
		NewsHMDL newsItem = new NewsHMDL();
		newsItem.setTitle("test title" + count);
		newsItem.setDescription("test description" + count);
		newsItem.setPublishDate(ZonedDateTime.now());
		Session session = HibernateUtils.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(newsItem);
			transaction.commit();
			return newsItem;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Create news item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static List<NewsHMDL> readAll(Session session) throws Exception {
		String sql = "SELECT n FROM " + NewsHMDL.class.getName() + " n " + "ORDER BY n.newsId";
		Query<NewsHMDL> query = null;
		query = session.createQuery(sql);
		List<NewsHMDL> newsList = query.getResultList();
		if (newsList.isEmpty())
			throw new Exception("News Table is empty..");
		return newsList;
	}

	public static NewsHMDL readSingle(Session session, long newsId) throws Exception {
		NewsHMDL newsHMDL = session.find(NewsHMDL.class, newsId);
		if (newsHMDL == null)
			throw new Exception("News Item not found..");
		return newsHMDL;
	}

	public static NewsHMDL update(Session session, long newsId) throws Exception {
		NewsHMDL newsItem = null;
		newsItem = session.get(NewsHMDL.class, newsId);
		if (newsItem == null)
			throw new Exception("News Id not found for Update..");
		newsItem.setPublishDate(ZonedDateTime.now().minusDays(1));
		session.update(newsItem);
		return newsItem;
	}

	public static Boolean delete(Session session, long newsId) throws Exception {
		NewsHMDL newsItem = null;
		newsItem = session.get(NewsHMDL.class, newsId);
		if (newsItem == null)
			throw new Exception("News Id not found to delete..");
		session.delete(newsItem);
		return true;
	}

}
