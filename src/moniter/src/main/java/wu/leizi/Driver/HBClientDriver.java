package wu.leizi.Driver;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import wu.leizi.moniterContent.configContent;

public class HBClientDriver implements HeartBeatDriver {
	private final Producer<String, String> producer;
	private HeartBeatThread HBThread;
	private String id;
	public HBClientDriver(String id) {
		producer = new Producer<String, String>(configContent.getInstance().ProduceConfig());
		this.id = id;
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
		return configContent.getInstance().getHBTopic();
	}

	public String getStatus() {
		// TODO Auto-generated method stub
//		String ret = "HB Status:" + HBThread.isAlive();
		return "alive";
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
	            String data = "ClientTest-" + id;
	            producer.send(new KeyedMessage<String, String>(configContent.getInstance().getHBTopic(), key ,data));
	            System.out.println(data);
	            try {
					Thread.sleep(configContent.getInstance().getHeartBeatInterval());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		
	}
}

