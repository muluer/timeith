package com.timeith.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import com.timeith.webapi.HelloServlet;

/**
 * Web Server
 *
 */
public class WebServer 
{
	public static Server createServer(int port) {
		Server server= new Server(port);
		ServletHandler servletHandler = new ServletHandler();
		server.setHandler(servletHandler);
		servletHandler.addServletWithMapping(HelloServlet.class, "/*");
		return server;
		
	}
	
    public static void main( String[] args )
    {
    	int port = 9090;
    	Server server = createServer(port);
    	try {
			server.start();
	        server.dumpStdErr();
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
