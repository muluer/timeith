package com.timeith.rss;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ContentFromAA {

	private static final String CONTENT_URI = "https://www.aa.com.tr";

	private Client client = ClientBuilder.newClient();

	public String getContentFromAA() {
		Response response = client
				.target(CONTENT_URI)
				.path("/")
				.request(MediaType.TEXT_HTML)
				.get();
		String resp = response.readEntity(String.class);
		return resp;
	}

	public static void main(String[] args) {
		ContentFromAA aa = new ContentFromAA();
		String str = aa.getContentFromAA();
		System.out.println(str);
	}

}
