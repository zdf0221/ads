package wu.leizi.config;

import java.sql.Connection;  
import java.sql.DriverManager;
import java.util.Properties;

public class mysqlConfig implements config {
	private String url;
	private String dataSet;
	private String ctrTable;
	private String adsTable;
	private static final String name = "com.mysql.jdbc.Driver";
	private String user;
	private String password;
	private static mysqlConfig instance = null;
	public Connection conn = null;
	private mysqlConfig() throws Exception {
		url = "jdbc:mysql://127.0.0.1/";
		dataSet = "ads";
		ctrTable = "ctr";
		adsTable = "ads";
		user = "root";
		password = "root";
	}
	
	public static mysqlConfig getInstance() throws Exception {
		if (instance == null) instance = new mysqlConfig();
		return instance;
	}
	
	public String getDataSet() {
		return url + dataSet;
	}
	
	public String getCtrTable() {
		return ctrTable;
	}
	
	public String getAdsTable() {
		return adsTable;
	}
	
	public String getUrl() {
		return url;
	}
	
	public Connection getConnect() throws Exception {
		if (conn == null || conn.isClosed()) {
			Class.forName(name);
			conn = DriverManager.getConnection(url + dataSet, user, password);
			System.out.println("Driver Load Success.");
		}
		return conn;
	}
	
	public void close() throws Exception {
		if (conn != null) conn.close();
	}
	
	public Properties getConnProp() {
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", user);
		connectionProperties.put("password", password);
		return connectionProperties;
	}
}
