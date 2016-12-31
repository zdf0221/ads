package wu.leizi.project.offline;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Row;

import wu.leizi.Driver.HeartBeat.HBClientDriver;
import wu.leizi.config.HDFSConfig;
import wu.leizi.config.mysqlConfig;
import wu.leizi.config.sparkConfig;
import org.apache.spark.sql.SaveMode;

public class adsStatisDriver extends HBClientDriver {
	private boolean isRunning;
	public adsStatisDriver(String id) {
		super(id+"-AdsStaticsDriver");
		isRunning = false;
		// TODO Auto-generated constructor stub
		
	}
	
	public void start() {
    	super.put();
    	
    }
    
	public void ctrStatis() throws Exception {
		isRunning = true;
		SparkSession sc = sparkConfig.getInstance().getSc();
		Dataset<Row> click = sc.read().json(HDFSConfig.getInstance().getClickUrl());
		Dataset<Row> put = sc.read().json(HDFSConfig.getInstance().getBidUrl());
		Dataset<Row> ans = click.join(put, click.col("putId").equalTo(put.col("id")), "outer");
		Dataset<Row> a = ans.select(put.col("adsId"), click.col("adsId")).groupBy(put.col("adsId").as("id"),click.col("adsId").as("label")).count();
		Dataset<Row> b = ans.select(put.col("adsId"), click.col("adsId")).groupBy(put.col("adsId").as("id")).count();
		a.createOrReplaceTempView("a");
		b.createOrReplaceTempView("b");
		Dataset<Row> ret=sc.sql("select a.id, a.count as click, b.count as total from a,b where a.id=b.id and a.label is not NULL");
		ret.show();
		String MYSQL_CONNECTION_URL = mysqlConfig.getInstance().getDataSet();
		ret.write().mode(SaveMode.Overwrite).jdbc(MYSQL_CONNECTION_URL, mysqlConfig.getInstance().getCtrTable(), mysqlConfig.getInstance().getConnProp());
		isRunning = false;
	}
	
	
    public String ServiceStatus() {
		return super.ServiceStatus() + " ads Statis Service:" + isRunning;
	}
	
	
}
