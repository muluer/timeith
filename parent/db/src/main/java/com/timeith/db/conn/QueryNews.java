package com.timeith.db.conn;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.timeith.models.NewsHMDL;

public class QueryNews {

	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			
			String sql= "SELECT n FROM " + NewsHMDL.class.getName() + " n " + "ORDER BY n.publishDate";
			@SuppressWarnings("unchecked")
			Query<NewsHMDL> query = session.createQuery(sql);
			List<NewsHMDL> newsList = query.getResultList();
			
			for (NewsHMDL newsHMDL : newsList) {
				System.out.println(newsHMDL.getNewsId() + "\t" + newsHMDL.getTitle() + "\t" + newsHMDL.getDescription() + "\t" + newsHMDL.getPublishDate());
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}

}
