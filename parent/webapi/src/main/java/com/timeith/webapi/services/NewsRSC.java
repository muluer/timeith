package com.timeith.webapi.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.timeith.db.conn.CRUDNews;
import com.timeith.db.conn.HibernateUtils;
import com.timeith.models.NewsHMDL;

@Path("/news")
public class NewsRSC {

	private Session session = null;
	private NewsHMDL newsItem = null;

	@Path("/create")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@QueryParam("count") int count) {
		try {
			newsItem = CRUDNews.create(count);
			return Response.status(Response.Status.CREATED).entity(newsItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@Path("/readall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAll() {
		session = HibernateUtils.getSession();
		List<NewsHMDL> newsList = null;
		try {
			newsList = CRUDNews.readAll(session);
			HibernateUtils.closeSession();
			return Response.status(Response.Status.OK).entity(newsList).build();
		} catch (Exception e) {
			HibernateUtils.closeSession();
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("/update")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@QueryParam("newsid") long newsId) {
		session = HibernateUtils.getSession();
		try {
			newsItem = CRUDNews.update(session, newsId);
			HibernateUtils.closeSession();
			return Response.status(Response.Status.OK).entity(newsItem).build();
		} catch (Exception e) {
			HibernateUtils.closeSession();
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("/readsingle")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSingle(@QueryParam("newsid") long newsId) {
		session = HibernateUtils.getSession();
		System.out.println("Test read single news item..");
		try {
			newsItem = CRUDNews.readSingle(session, newsId);
			HibernateUtils.closeSession();
			return Response.status(Response.Status.OK).entity(newsItem).build();
		} catch (Exception e) {
			HibernateUtils.closeSession();
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("/delete")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("newsid") long newsId) {
		session = HibernateUtils.getSession();
		System.out.println("Test delete news item..");
		Boolean isDeleted = false;
		try {
			isDeleted = CRUDNews.delete(session, newsId);
			HibernateUtils.closeSession();
			return Response.status(Response.Status.OK).entity(isDeleted).build();
		} catch (Exception e) {
			HibernateUtils.closeSession();
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

}
