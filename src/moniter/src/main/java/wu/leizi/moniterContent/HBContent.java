package wu.leizi.moniterContent;

import java.util.ArrayList;
import java.util.Hashtable;

import wu.leizi.Driver.Driver;
import wu.leizi.Driver.DriverFactory;

public class HBContent {
	private Hashtable<String, Driver> DriverList;
	private Hashtable<String, Boolean> checkList;
	public HBContent() {
		DriverList = new Hashtable<String, Driver>();
		checkList = new Hashtable<String, Boolean>();
	}
	
	public ArrayList<String> getStatus() {
		ArrayList<String> ret = new ArrayList<String>();
		System.out.println("==================\nResult:");
		for (String ent : DriverList.keySet()) {
			ret.add(ent + ":\t" + DriverList.get(ent).getStatus());
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
	
	public void active(String ent) {
		Driver ans = DriverList.get(ent);
		if (ans == null){
			ans = DriverFactory.factory(ent.split("-"));
			DriverList.put(ent, ans);
		}
		checkList.put(ent, true);
	}
}
