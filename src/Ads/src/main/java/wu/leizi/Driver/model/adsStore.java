package wu.leizi.Driver.model;

import java.util.HashMap;

import wu.leizi.Driver.Driver;
import wu.leizi.config.mysqlConfig;

public class adsStore implements Driver {
	
	@Override
	public void put() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getStatus() throws Exception {
		// TODO Auto-generated method stub
		if (mysqlConfig.getInstance().getConnect() == null || mysqlConfig.getInstance().getConnect().isClosed()) return "Ads Set connect failed.";
		else return "ads set connect success.";
	}

	@Override
	public HashMap<String, String> get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
