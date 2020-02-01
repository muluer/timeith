package com.timeith.db.conn;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.timeith.db.utils.HibernateUtilsDB;
import com.timeith.models.RSSLinksHMDL;

@SuppressWarnings("unchecked")
public class CRUDRssLinksDB {

	public static RSSLinksHMDL create(RSSLinksHMDL rssLink) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			long rssLinkId = (long) session.save(rssLink);
			if (rssLinkId < 1) {
				throw new Exception("RSS Link ID not generated..");
			} else {
				transaction.commit();
				return rssLink;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Create RSS item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static List<RSSLinksHMDL> readAll() throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		Query<RSSLinksHMDL> query = null;
		try {
			transaction = session.beginTransaction();
			String sql = "SELECT n FROM " + RSSLinksHMDL.class.getName() + " n " + "ORDER BY n.rssLinkId";
			query = session.createQuery(sql);
			List<RSSLinksHMDL> rssItemList = query.getResultList();
			if (rssItemList.isEmpty())
				throw new Exception("RSS Links Table is empty..");
			else {
				transaction.commit();
				return rssItemList;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Read all RSS items failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static RSSLinksHMDL readSingle(long rssLinkId) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			RSSLinksHMDL rssLinkItem = session.find(RSSLinksHMDL.class, rssLinkId);
			if (rssLinkItem == null)
				throw new Exception("RSS Item not found..");
			else {
				transaction.commit();
				return rssLinkItem;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Read single RSS item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static RSSLinksHMDL update(RSSLinksHMDL rssLinkItem) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		RSSLinksHMDL rssLinkTmp = null;
		try {
			transaction = session.beginTransaction();
			rssLinkTmp = session.get(RSSLinksHMDL.class, rssLinkItem.getRssLinkId());
			if (rssLinkTmp == null)
				throw new Exception("RSS Link Id not found for Update..");
			else {
				rssLinkTmp.setRsslinkcategory(rssLinkItem.getRsslinkcategory());
				rssLinkTmp.setRsslinkdescription(rssLinkItem.getRsslinkdescription());
				rssLinkTmp.setRsslinkurl(rssLinkItem.getRsslinkurl());
				session.update(rssLinkTmp);
				transaction.commit();
				return rssLinkTmp;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Update RSS Link item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static Boolean delete(long rssLinkId) throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		RSSLinksHMDL rssLinkItem = null;
		try {
			transaction = session.beginTransaction();
			rssLinkItem = session.get(RSSLinksHMDL.class, rssLinkId);
			if (rssLinkItem == null)
				throw new Exception("RSS item Id not found to delete..");
			else {
				session.delete(rssLinkItem);
				transaction.commit();
				return true;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Delete RSS item failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static int deleteAll() throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		Query<RSSLinksHMDL> query = null;
		try {
			transaction = session.beginTransaction();
			String sql = "DELETE FROM " + RSSLinksHMDL.class.getName();
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
			throw new Exception("Delete all RSS items failed..", e.getCause());
		} finally {
			session.close();
		}
	}

	public static int countAll() throws Exception {
		Session session = HibernateUtilsDB.getSession();
		Transaction transaction = null;
		Query<RSSLinksHMDL> query = null;
		try {
			transaction = session.beginTransaction();
			String sql = "SELECT COUNT(*) FROM " + RSSLinksHMDL.class.getName();
			query = session.createQuery(sql);
			int itemCount = query.list().size();
			if (itemCount == 0)
				throw new Exception("Table is empty..");
			else {
				transaction.commit();
				return itemCount;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw new Exception("Count RSS items failed..", e.getCause());
		} finally {
			session.close();
		}
	}

}
