package wu.leizi.Driver.heartbeat;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import wu.leizi.config.HBConfig;
import wu.leizi.data.HeartBeatData;

public class HBClientDriver implements HeartBeatDriver {
	private final Producer<String, String> producer;
	private HeartBeatThread HBThread;
	private String id;
	private String content;
	public HBClientDriver(String id, String content) {
		producer = new Producer<String, String>(HBConfig.getInstance().ProduceConfig());
		this.id = id;
		this.content = content;
	}
	
	public void put() {
		// TODO Auto-generated method stub
		HBThread = new HeartBeatThread();
		HBThread.start();
	}

	public void get() {
		// TODO Auto-generated method stub
		
	}

	public String getTopic() {
		// TODO Auto-generated method stub
		return HBConfig.getInstance().getHBTopic();
	}

	public String getStatus() {
		// TODO Auto-generated method stub
//		String ret = "HB Status:" + HBThread.isAlive();
		return content;
	}
	
	public String ServiceStatus(){
		String ret = "HBService:" + HBThread.isAlive();
		return ret;
	}
	
	class HeartBeatThread extends Thread {
		
		public void run() {
			// TODO Auto-generated method stub
			Task();
		}
		private void Task() {
			// TODO Auto-generated method stub
			long no = 0;
	        while (true) {
	        	String key = String.valueOf(no++);
	            HeartBeatData data = new HeartBeatData("HBClient", "HBClient-"+id);
	            data.put("content", ServiceStatus());
	            producer.send(new KeyedMessage<String, String>(HBConfig.getInstance().getHBTopic(), key ,data.toString()));
//	            System.out.println(data);
	            try {
					Thread.sleep(HBConfig.getInstance().getHeartBeatInterval());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		
	}
}

