package wu.leizi.project.moniter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import wu.leizi.Content.HBContent;
import wu.leizi.config.HBConfig;

@SuppressWarnings("restriction")
public class moniterHttpServer extends Thread {
	private HBContent HBcontent;
	public moniterHttpServer(HBContent hb) {
		HBcontent = hb;
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			InetSocketAddress addr = new InetSocketAddress(HBConfig.getInstance().getServerWebPort());  
	        HttpServer server = HttpServer.create(addr, 0);  
	        server.createContext("/", new MyHandler());  
	        server.setExecutor(Executors.newCachedThreadPool());  
	        server.start();  
//	        System.out.println("Server Thread started on " +HBConfig.getInstance().getServerWebPort()); 
		} catch (Exception e) {
			// TODO: handle exception
		} 
	}
	class MyHandler implements HttpHandler {
	    
	    public void handle(HttpExchange exchange) throws IOException{  
	          
	        String requestMethod = exchange.getRequestMethod(); 
	        if (requestMethod.equalsIgnoreCase("GET")) {  
	            Headers responseHeaders = exchange.getResponseHeaders();  
	            responseHeaders.set("Content-Type", "text/html");  
	            exchange.sendResponseHeaders(200, 0);
				try {
					String ret = HBcontent.getStatus();
					OutputStream responseBody = exchange.getResponseBody();  
		            responseBody.write(ret.getBytes());
		            responseBody.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        }
	         
	    }
	} 
}
