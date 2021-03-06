package wu.leizi.config;

public class HDFSConfig implements config {
	private String clickUrl;
	private String bidUrl;
	private static HDFSConfig instance = null;
	
	private HDFSConfig() {
		clickUrl = "hdfs://127.0.0.1:9000/offline/click/";
		bidUrl = "hdfs://127.0.0.1:9000/offline/put/";
	}
	
	public static HDFSConfig getInstance() {
		if (instance == null) instance = new HDFSConfig();
		return instance;
	}
	
	public String getClickUrl() {
		return clickUrl;
	}
	
	public String getBidUrl() {
		return bidUrl;
	}
	
}
