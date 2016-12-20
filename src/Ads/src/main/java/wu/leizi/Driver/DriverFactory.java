package wu.leizi.Driver;


import wu.leizi.Driver.heartbeat.HBClientDriver;
import wu.leizi.data.Data;

public class DriverFactory {
	
	public static Driver factory(Data ent) throws Exception {
		if (ent.get("Driver").equals("HBClient")) {
			System.out.print("New HB Client");
			return new HBClientDriver((String) ent.get("Id"), (String) ent.get("content"));
		}
		else if (ent.get("Driver").equals("HBServer")) {
//			System.out.print("New HB Server");
//			return new HBServerDriver();
			System.out.println("HBServer Driver can only be created by moniter class.");
			throw new Exception();
		}
		else return null;
	}
}
