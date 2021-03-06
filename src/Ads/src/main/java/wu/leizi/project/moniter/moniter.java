package wu.leizi.project.moniter;

import wu.leizi.Content.HBContent;
import wu.leizi.Content.contentFactory;
import wu.leizi.Driver.HeartBeat.HBServerDriver;
import wu.leizi.config.HBConfig;

public class moniter {
	private HBContent HBcontent;
	private HBServerDriver HBserver;
	private moniterHttpServer web;
	public moniter() {
		// TODO Auto-generated constructor stub
		
		// HB moniter content
		HBcontent = contentFactory.createHBC();
		
		// HB moniter server kafka server
		HBserver = new HBServerDriver(HBcontent);
		
		// web
		web = new moniterHttpServer(HBcontent);
		
	}
	
	public void start() {
		HBserver.start();
		System.out.println("Moniter Loop started.");
		
		web.start();
		System.out.println("Web Started on Port " + HBConfig.getInstance().getServerWebPort());
		
	}
}
