package wu.leizi.project.webApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
//import java.util.UUID;
import java.util.concurrent.Executors;  
  
import com.sun.net.httpserver.Headers;  
import com.sun.net.httpserver.HttpExchange;  
import com.sun.net.httpserver.HttpHandler;  
import com.sun.net.httpserver.HttpServer;

import wu.leizi.Driver.HeartBeat.HBClientDriver;

import org.json.*;  
public class webApi extends HBClientDriver { 
	private InetSocketAddress addr;
	private HttpServer server;
    public webApi(String id, String content) throws IOException {
		super("WebApi"+id);
		// TODO Auto-generated constructor stub
		addr = new InetSocketAddress(8080);  
        server = HttpServer.create(addr, 0);  
  
        server.createContext("/api", new MyHandler());  
	}
    
    public void start() {
    	super.put();
    	server.setExecutor(Executors.newCachedThreadPool());  
        server.start();  
        System.out.println("Server is listening on port 8080"); 
    }
    
    public String ServiceStatus() {
		return super.ServiceStatus() + " HttpStatus: alive";
	}
    
}  
  
class MyHandler implements HttpHandler {
      
    public void handle(HttpExchange exchange) throws IOException{  
          
        String requestMethod = exchange.getRequestMethod();  
        System.out.println("Handling:"+requestMethod); 
        if (requestMethod.equalsIgnoreCase("GET")) {  
            Headers responseHeaders = exchange.getResponseHeaders();  
            responseHeaders.set("Content-Type", "application/json");  
            exchange.sendResponseHeaders(200, 0);  
            OutputStream responseBody = exchange.getResponseBody();  
            JSONObject json=new JSONObject(); 
            try {
            	JSONArray jsonMembers = new JSONArray();  
                JSONObject member1 = new JSONObject();  
				member1.put("content", "baidu");
				member1.put("url", "http://www.baidu.com");   
	            jsonMembers.put(member1);  
	          
	            JSONObject member2 = new JSONObject();  
	            member2.put("content", "taobao");  
	            member2.put("url", "http://www.taobao.com");  
	            jsonMembers.put(member2); 
//	            System.out.println(Thread.currentThread().getId()+"--"+ManagementFactory.getRuntimeMXBean().getName().split("@")[0]+"--"+System.currentTimeMillis());
	            String AdsId = String.format("%013d%04d", System.currentTimeMillis(), Thread.currentThread().getId());
	            System.out.println(AdsId);
//	            System.out.println(UUID.randomUUID().toString());
	            json.put("AdsId", AdsId);
	            json.put("AdsItem", jsonMembers);
	            responseBody.write(json.toString().getBytes());
	            offlineLog.getInstance().put(json.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
            responseBody.close();  
        }
        if (requestMethod.equalsIgnoreCase("POST")) {  
            Headers responseHeaders = exchange.getResponseHeaders();  
            responseHeaders.set("Content-Type", "application/json");  
            exchange.sendResponseHeaders(200, 0);  
            OutputStream responseBody = exchange.getResponseBody();
            try {
            	JSONObject jsonDate = getJsonDate(exchange.getRequestBody());
            	if (jsonDate.has("size")){
            		JSONArray jsonMembers = new JSONArray();
            		String putIdAns = String.format("%013d%04d", System.currentTimeMillis(), Thread.currentThread().getId());
            		Integer size = jsonDate.getInt("size");
            		for (int i = 0; i < size; i++){
            			JSONObject member = new JSONObject(); 
            			member.put("content", "baidu");
        				member.put("url", "http://www.baidu.com");
        				member.put("adsId", i);
        				member.put("id", String.format("%s%03d", putIdAns, i));
        				jsonMembers.put(member);
            		}
            		if (jsonDate.has("cookieId")){
            			responseBody.write(jsonMembers.toString().getBytes());
            		}
            		else{
            			JSONObject json = new JSONObject();
            			String AdsId = String.format("%013d%04d", System.currentTimeMillis(), Thread.currentThread().getId());
        	            System.out.println(AdsId);
//        	            System.out.println(UUID.randomUUID().toString());
        	            json.put("AdsId", AdsId);
        	            json.put("AdsItem", jsonMembers);
        	            responseBody.write(json.toString().getBytes());
            		}
            		System.out.println("a");
    	            offlineLog.getInstance().put(jsonMembers.toString());
            	}
            	if (jsonDate.has("putId")){
            		JSONObject json = new JSONObject();
            		json.put("Status", "Success");
            		responseBody.write(json.toString().getBytes());
            		offlineLog.getInstance().click(jsonDate.toString());
            	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Execpt");
				e.printStackTrace();
			}    
            responseBody.close();  
        }  
    }
    public static JSONObject getJsonDate(InputStream input) throws IOException, JSONException{
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        String ret = "";
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null){
        	ret += inputLine;
        }
        JSONObject json = new JSONObject(ret);
        System.out.println(json.toString());
        return json;
        
    }
}  