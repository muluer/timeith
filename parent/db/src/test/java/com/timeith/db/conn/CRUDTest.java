package com.timeith.db.conn;

import org.hibernate.Session;

public class CRUDTest {

	private static Session session = null;

	public static void main(String[] args) {
		
		try {
			session = HibernateUtils.getSession();
			CRUDNews.create(session, 41);
			HibernateUtils.closeSession();
			
			session = HibernateUtils.getSession();
			CRUDNews.readAll(session);
			HibernateUtils.closeSession();
			
			session = HibernateUtils.getSession();
			CRUDNews.update(session, 44L);
			HibernateUtils.closeSession();
			
			session = HibernateUtils.getSession();
			CRUDNews.delete(session, 44L);
			HibernateUtils.closeSession();

			session = HibernateUtils.getSession();
			CRUDNews.readAll(session);
			HibernateUtils.closeSession();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		HibernateUtils.shutdown();
	}
}
