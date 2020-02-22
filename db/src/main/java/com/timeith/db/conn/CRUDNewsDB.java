package com.timeith.db.conn;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.timeith.db.utils.HibernateUtilsDB;
import com.timeith.models.NewsHMDL;

@SuppressWarnings("unchecked")
public class CRUDNewsDB {

	public static NewsHMDL create(NewsHMDL newsItem) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		long newsId = -1;
		try {
			transaction = session.beginTransaction();
			newsId = (long) session.save(newsItem);
			if (newsId == -1) {
				throw new Exception("News ID not generated..");
			} else {
				transaction.commit();
				return newsItem;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Create news item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static List<NewsHMDL> readAll() throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		Query<NewsHMDL> query = null;
		try {
			transaction = session.beginTransaction();
			String sql = "SELECT n FROM " + NewsHMDL.class.getName() + " n " + "ORDER BY n.newsId";
			query = session.createQuery(sql);
			List<NewsHMDL> newsList = query.getResultList();
			if (newsList.isEmpty())
				throw new Exception("News Table is empty..");
			else {
				transaction.commit();
				return newsList;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Read all news item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static NewsHMDL readSingle(long newsId) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NewsHMDL newsHMDL = session.find(NewsHMDL.class, newsId);
			if (newsHMDL == null)
				throw new Exception("News Item not found..");
			else {
				transaction.commit();
				return newsHMDL;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Read single news item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static NewsHMDL update(NewsHMDL newsHMDL) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		NewsHMDL newsItem = null;
		try {
			transaction = session.beginTransaction();
			newsItem = session.get(NewsHMDL.class, newsHMDL.getNewsId());
			if (newsItem == null)
				throw new Exception("News Id not found for Update..");
			else {
				newsItem.setTitle(newsHMDL.getTitle());
				newsItem.setDescription(newsHMDL.getDescription());
				newsItem.setPublishDate(newsHMDL.getPublishDate());
				session.update(newsItem);
				transaction.commit();
				return newsItem;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Update news item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static Boolean delete(long newsId) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		NewsHMDL newsItem = null;
		try {
			transaction = session.beginTransaction();
			newsItem = session.get(NewsHMDL.class, newsId);
			if (newsItem == null)
				throw new Exception("News Id not found to delete..");
			else {
				session.delete(newsItem);
				transaction.commit();
				return true;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Delete news item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static int deleteAll() throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		Query<NewsHMDL> query = null;
		try {
			transaction = session.beginTransaction();
			String sql = "DELETE FROM " + NewsHMDL.class.getName();
			query = session.createQuery(sql);
			int itemCount = query.executeUpdate();
			if (itemCount == 0)
				throw new Exception("Table is empty..");
			else {
				transaction.commit();
				return itemCount;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Delete all news failed..", e.getCause());
		} finally {
			session.close();
		}
	}
	
	public static long countAll() throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		Query<Long> query = null;
		try {
			transaction = session.beginTransaction();
			String sql = "SELECT COUNT(*) FROM " + NewsHMDL.class.getName();
			query = session.createQuery(sql);
			long itemCount = query.getSingleResult();
			if (itemCount == 0)
				throw new Exception("Table is empty..");
			else {
				transaction.commit();
				return itemCount;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Count News items failed..", e.getCause());
		} finally {
			session.close();
		}
	}

}
