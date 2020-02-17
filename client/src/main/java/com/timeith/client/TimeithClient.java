package com.timeith.client;

import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import com.timeith.models.NewsHMDL;

/**
 * Timeith Client
 *
 */
public class TimeithClient {
	private static Client client;
	
	public TimeithClient() {
	}

	public static InputStream getRSS(String content_uri) {
		init();
		Response response = client
				.target(content_uri)
				.path("/")
				.request()
				.get();
		InputStream contentBody = response.readEntity(InputStream.class);
		close();
		return contentBody;
	}
	
	public static NewsHMDL createRssItem(String content_uri, NewsHMDL newsHMDL) throws Exception {
		init();
		NewsHMDL contentBody = null;
		Response response = client
				.target(content_uri)
				.path("/news/create")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("Charset", "UTF-8")
				.post(Entity.json(newsHMDL));
		if (response.getStatus() != 200) {
			throw new Exception(response.readEntity(String.class));
		} else {
			contentBody = response.readEntity(NewsHMDL.class);	
		}
		close();
		return contentBody;
	}

	public static NewsHMDL readRssItem(String content_uri, long newsId) throws Exception {
		init();
		NewsHMDL contentBody = null;
		Response response = client
				.target(content_uri)
				.path("/news/readsingle")
				.queryParam("newsid", newsId)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("Charset", "UTF-8")
				.get();
		if (response.getStatus() != 200) {
			throw new Exception(response.readEntity(String.class));
		} else {
			contentBody = response.readEntity(NewsHMDL.class);	
		}
		close();
		return contentBody;
	}
	
	public static void init() {
		client = ClientBuilder.newClient();
	}
	
	public static void close() {
		client.close();
	}
	
}
