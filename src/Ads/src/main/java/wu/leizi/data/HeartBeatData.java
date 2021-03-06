package wu.leizi.data;

import org.json.JSONObject;

public class HeartBeatData implements Data {
	private JSONObject data;
	public HeartBeatData(String content) {
		data = new JSONObject(content);
		data.put("Type", "HeartBeat");
	}
	public HeartBeatData(String driver, String id){
		data = new JSONObject();
		data.put("Type", "HeartBeat");
		data.put("Driver", driver);
		data.put("Id", id);
	}
	public <T> void put(String key, T value) {
		data.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		T ret =  (T) data.get(key);
		return ret;
	}
	
	public String toString() {
		return data.toString();
	}
}
