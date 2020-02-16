package com.timeith.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.timeith.models.NewsHMDL;

/**
 * Timeith Client
 *
 */
public class TimeithClientPost {
	private Client client = ClientBuilder.newClient();

	public NewsHMDL getRSS(String content_uri, NewsHMDL newsHMDL) {
		Response response = client
				.target(content_uri)
				.path("/news/create")
				.request()
				.post(Entity.json(newsHMDL));
		NewsHMDL contentBody = response.readEntity(NewsHMDL.class);
		return contentBody;
	}
	
}
