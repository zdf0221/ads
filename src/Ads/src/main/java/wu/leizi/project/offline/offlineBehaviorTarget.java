package wu.leizi.project.offline;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import wu.leizi.Driver.HeartBeat.HBClientDriver;
import wu.leizi.config.HDFSConfig;
import wu.leizi.config.sparkConfig;

public class offlineBehaviorTarget extends HBClientDriver {
	private boolean isRunning;
	public offlineBehaviorTarget(String id) {
		super(id + "-offlineBehaviorTarget");
		// TODO Auto-generated constructor stub
		isRunning = false;
	}
	
	public void start() {
		super.put();
	}
	
	public void offlineBT() {
		isRunning = true;
		SparkSession sc = sparkConfig.getInstance().getSc();
		Dataset<Row> click = sc.read().json(HDFSConfig.getInstance().getClickUrl());
		click.show();
	}
	
	public String ServiceStatus() {
		return super.ServiceStatus() + " offlineBehaviorTagart:" + isRunning;
	}
	
	public void close() {
		super.close();
		isRunning = false;
	}
}
