package com.timeith.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ContentFromAAExample {

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
		ContentFromAAExample aa = new ContentFromAAExample();
		String str = aa.getContentFromAA();
		System.out.println(str);
	}

}
