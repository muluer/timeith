package com.timeith.webapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.timeith.db.conn.CRUDNews;
import com.timeith.models.NewsHMDL;

@Path("/news")
public class NewsRSC {

	private NewsHMDL newsItem = null;

	@Path("/create")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@QueryParam("count") int count) {
		try {
			newsItem = CRUDNews.create(count);
			return Response.status(Response.Status.OK).entity(newsItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/readall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAll() {
		List<NewsHMDL> newsList = null;
		try {
			newsList = CRUDNews.readAll();
			return Response.status(Response.Status.OK).entity(newsList).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/update")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@QueryParam("newsid") long newsId) {
		try {
			newsItem = CRUDNews.update(newsId);
			return Response.status(Response.Status.OK).entity(newsItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/readsingle")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSingle(@QueryParam("newsid") long newsId) {
		System.out.println("Test read single news item..");
		try {
			newsItem = CRUDNews.readSingle(newsId);
			return Response.status(Response.Status.OK).entity(newsItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/delete")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("newsid") long newsId) {
		System.out.println("Test delete news item..");
		Boolean isDeleted = false;
		try {
			isDeleted = CRUDNews.delete(newsId);
			return Response.status(Response.Status.OK).entity(isDeleted).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/deleteall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete() {
		System.out.println("Test delete all news item..");
		try {
			int itemCount = CRUDNews.deleteAll();
			return Response.status(Response.Status.OK).entity(itemCount).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

}
