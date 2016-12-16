package wu.leizi.moniterContent;

import java.util.ArrayList;
import java.util.Hashtable;

import wu.leizi.Driver.Driver;
import wu.leizi.Driver.DriverFactory;
import wu.leizi.data.HeartBeatData;

public class HBContent {
	private Hashtable<String, Driver> DriverList;
	private Hashtable<String, Boolean> checkList;
	public HBContent() {
		DriverList = new Hashtable<String, Driver>();
		checkList = new Hashtable<String, Boolean>();
	}
	
	public String getStatus() {
		String ret = "<h1>Result:</h1>";
		System.out.println("==================\nResult:");
		for (String ent : DriverList.keySet()) {
			ret += ent + ": " + DriverList.get(ent).getStatus()+"<br>";
			System.out.println(ent + " " + DriverList.get(ent).getStatus());
		}
		System.out.println();
		return ret;
	}
	
	public void check() {
		for (String ent : checkList.keySet()) {
			if (! checkList.get(ent)) DriverList.remove(ent);
			checkList.put(ent, false);
		}
	}
	
	public void active(HeartBeatData ent) {
		Driver ans = DriverList.get(ent.get("Id"));
		String id = (String) ent.get("Id");
		if (ans == null){
			ans = DriverFactory.factory(ent);
			DriverList.put(id, ans);
		}
		checkList.put(id, true);
	}
}
