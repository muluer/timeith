package com.timeith.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeith.server.schedule.JobScheduler;
import com.timeith.webapi.HelloServlet;

/**
 * 
 * Web Server
 *
 */
public class WebServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

	public static Server createServer(int port) {
		LOGGER.debug("jetty starting..");
		Server jettyServer = new Server();
		ServerConnector jettyServerConnector = new ServerConnector(jettyServer);
		jettyServerConnector.setPort(port);
		jettyServerConnector.setIdleTimeout(300000);
		jettyServer.addConnector(jettyServerConnector);

		ResourceConfig jerseyResourceConfig = new ResourceConfig();
		jerseyResourceConfig.packages("com.timeith.webapi.resources");
		ServletContainer jerseyServletContainer = new ServletContainer(jerseyResourceConfig);
		ServletHolder webapiServletHolder = new ServletHolder(jerseyServletContainer);

		ServletContextHandler jettyServletContextHandler1 = new ServletContextHandler();
		jettyServletContextHandler1.setContextPath("/webapi/v1");
		jettyServletContextHandler1.addServlet(webapiServletHolder, "/*");

		ServletContextHandler jettyServletContextHandler2 = new ServletContextHandler();
		jettyServletContextHandler2.setContextPath("/scheduler/");

		JobScheduler jobScheduler = new JobScheduler();
		ServletHolder jServletHolder = new ServletHolder(jobScheduler);
		jettyServletContextHandler2.addServlet(jServletHolder, "/*");

		ServletContextHandler jettyServletContextHandler3 = new ServletContextHandler();
		jettyServletContextHandler3.setContextPath("/health/");
		HelloServlet helloServlet = new HelloServlet();
		ServletHolder helloHolder = new ServletHolder(helloServlet);
		jettyServletContextHandler3.addServlet(helloHolder, "/*");

		ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection(jettyServletContextHandler1,
				jettyServletContextHandler2, jettyServletContextHandler3);
		jettyServer.setHandler(contextHandlerCollection);

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

/*
 * 2. resource eklemek için
 *
 * ResourceConfig jerseyResourceConfig2 = new ResourceConfig();
 * jerseyResourceConfig2.packages("com.timeith.twitter.resources");
 * ServletContainer jerseyServletContainer2 = new
 * ServletContainer(jerseyResourceConfig2); ServletHolder webapiServletHolder2 =
 * new ServletHolder(jerseyServletContainer2);
 * 
 * ServletContextHandler jettyServletContextHandler2 = new
 * ServletContextHandler();
 * jettyServletContextHandler2.setContextPath("/webapi/v1");
 * jettyServletContextHandler2.addServlet(webapiServletHolder2, "/*");
 * //jettyServer.setHandler(jettyServletContextHandler);
 * 
 * ContextHandlerCollection contextHandlerCollection = new
 * ContextHandlerCollection(jettyServletContextHandler1);
 * //(,jettyServletContextHandler2)
 * jettyServer.setHandler(contextHandlerCollection);
 */
