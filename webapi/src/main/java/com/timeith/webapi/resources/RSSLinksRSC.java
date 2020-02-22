package com.timeith.webapi.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.timeith.db.conn.CRUDRssLinksDB;
import com.timeith.models.RSSLinksHMDL;

@Path("/rsslinks")
public class RSSLinksRSC {

	private RSSLinksHMDL rssItem = null;

	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(RSSLinksHMDL rssLink) {
		try {
			rssItem = CRUDRssLinksDB.create(rssLink);
			return Response.status(Response.Status.OK).entity(rssItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/readall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAll() {
		List<RSSLinksHMDL> rssLinkList = null;
		try {
			rssLinkList = CRUDRssLinksDB.readAll();
			return Response.status(Response.Status.OK).entity(rssLinkList).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(RSSLinksHMDL rssLink) {
		try {
			rssItem = CRUDRssLinksDB.update(rssLink);
			return Response.status(Response.Status.OK).entity(rssItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/readsingle")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSingle(@QueryParam("rssLinkId") long rssLinkId) {
		System.out.println("Test read single news item..");
		try {
			rssItem = CRUDRssLinksDB.readSingle(rssLinkId);
			return Response.status(Response.Status.OK).entity(rssItem).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/delete")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("rssLinkId") long rssLinkId) {
		System.out.println("Test delete RSS item..");
		Boolean isDeleted = false;
		try {
			isDeleted = CRUDRssLinksDB.delete(rssLinkId);
			return Response.status(Response.Status.OK).entity(isDeleted).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/deleteall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAll() {
		System.out.println("Test delete all RSS items..");
		try {
			int itemCount = CRUDRssLinksDB.deleteAll();
			return Response.status(Response.Status.OK).entity(itemCount).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}

	@Path("/count")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response count() {
		System.out.println("Test count all RSS items..");
		try {
			long itemCount = CRUDRssLinksDB.countAll();
			return Response.status(Response.Status.OK).entity(itemCount).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}
	
}
