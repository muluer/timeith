package com.timeith.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Web Server
 *
 */
public class WebServer {
	public static Server createServer(int port) {
		Server jettyServer = new Server();
		ServerConnector jettyServerConnector = new ServerConnector(jettyServer);
		jettyServerConnector.setPort(port);
		jettyServerConnector.setIdleTimeout(300000);
		jettyServer.addConnector(jettyServerConnector);
		
		
		ResourceConfig jerseyResourceConfig = new ResourceConfig();
		jerseyResourceConfig.packages("com.timeith.webapi.resources");
		ServletContainer jerseyServletContainer = new ServletContainer(jerseyResourceConfig);
		ServletHolder webapiServletHolder = new ServletHolder(jerseyServletContainer);
		
		ServletContextHandler jettyServletContextHandler = new ServletContextHandler();
		jettyServletContextHandler.setContextPath("/webapi/v1");
		jettyServletContextHandler.addServlet(webapiServletHolder, "/*");
		jettyServer.setHandler(jettyServletContextHandler);
		
		return jettyServer;

	}

	public static void main(String[] args) {
		int port = 9090;
		Server server = createServer(port);
		try {
			server.start();
			server.dumpStdErr();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
