package wu.leizi.Content;

import java.util.Hashtable;

import wu.leizi.Driver.DriverFactory;
import wu.leizi.Driver.HeartBeat.HBClientDriver;
import wu.leizi.data.HeartBeatData;

public class HBContent {
	private Hashtable<String, HBClientDriver> DriverList;
	private Hashtable<String, Boolean> checkList;
	public HBContent() {
		DriverList = new Hashtable<String, HBClientDriver>();
		checkList = new Hashtable<String, Boolean>();
	}
	
	public String getStatus() throws Exception {
		String ret = "<h1>Result:</h1><table border='1'>"
				+ "<tr>"
				+ "<td> Driver ID </td>"
				+ "<td> Status </td>"
				+ "</tr>";
//		System.out.println("==================\nResult:");
		for (String ent : DriverList.keySet()) {
			ret += "<tr><td>"+ent + "</td><td>" + DriverList.get(ent).getStatus()+"</td></tr>";
//			System.out.println(ret);
//			System.out.println(ent + " " + DriverList.get(ent).getStatus());
		}
		ret += "</table>";
//		System.out.println();
		return ret;
	}
	
	public void check() {
		for (String ent : checkList.keySet()) {
			if (! checkList.get(ent)) DriverList.remove(ent);
			checkList.put(ent, false);
		}
	}
	
	public void active(HeartBeatData ent) throws Exception {
		HBClientDriver ans = DriverList.get(ent.get("Id"));
		String id = (String) ent.get("Id");
		if (ans == null){
			ans = (HBClientDriver)DriverFactory.factory(ent);
			DriverList.put(id, ans);
		}
		ans.setContent(ent.get("content"));
		checkList.put(id, true);
	}
}
