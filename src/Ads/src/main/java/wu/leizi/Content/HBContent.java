package wu.leizi.Content;

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
		String ret = "<h1>Result:</h1><table border='1'>"
				+ "<tr>"
				+ "<td> Driver ID </td>"
				+ "<td> Status </td>"
				+ "</tr><tr>";
		System.out.println("==================\nResult:");
		for (String ent : DriverList.keySet()) {
			ret += "<td>"+ent + "</td><td>" + DriverList.get(ent).getStatus()+"</td>";
			System.out.println(ent + " " + DriverList.get(ent).getStatus());
		}
		ret += "</tr></table>";
		System.out.println();
		return ret;
	}
	
	public void check() {
		for (String ent : checkList.keySet()) {
			if (! checkList.get(ent)) DriverList.remove(ent);
			checkList.put(ent, false);
		}
	}
	
	public void active(HeartBeatData ent) throws Exception {
		Driver ans = DriverList.get(ent.get("Id"));
		String id = (String) ent.get("Id");
		if (ans == null){
			ans = DriverFactory.factory(ent);
			DriverList.put(id, ans);
		}
		checkList.put(id, true);
	}
}
