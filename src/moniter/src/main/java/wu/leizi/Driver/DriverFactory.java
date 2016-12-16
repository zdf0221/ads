package wu.leizi.Driver;

import wu.leizi.data.HeartBeatData;

public class DriverFactory {
	
	public static Driver factory(HeartBeatData ent) {
		if (ent.get("Driver").equals("HBClient")) {
			System.out.print("New Client");
			return new HBClientDriver((String) ent.get("Id"), (String) ent.get("content"));
		}
		else {
			System.out.println("New Server");
			return new HBServerDriver();
		}
	}
}
