package com.timeith.client;

import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * Timeith Client
 *
 */
public class TimeithClient 
{
	private Client client = ClientBuilder.newClient();

	public InputStream getRSS(String content_uri) {
		Response response = client
				.target(content_uri)
				.path("/")
				.request()
				.get();
		InputStream contentBody = response.readEntity(InputStream.class);
		return contentBody;
	}
	
}
