package com.timeith.db.conn;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.timeith.db.utils.HibernateUtilsDB;
import com.timeith.models.NewsHMDL;

public class CriteriaTest {

	public static void main(String[] args) {
		
		Session session = HibernateUtilsDB.getSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<NewsHMDL> criteriaQuery = criteriaBuilder.createQuery(NewsHMDL.class);
		Root<NewsHMDL> root = criteriaQuery.from(NewsHMDL.class);
		criteriaQuery.select(root);
		
		Query<NewsHMDL> query = session.createQuery(criteriaQuery);
		List<NewsHMDL> newsItemList = query.getResultList();
		
		newsItemList.stream().forEach(item -> System.out.println(item.toString()));
		
	}

}
