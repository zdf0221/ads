package wu.leizi.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import wu.leizi.data.HeartBeatData;
import wu.leizi.moniterContent.HBContent;
import wu.leizi.moniterContent.configContent;
import wu.leizi.moniterContent.contentFactory;

public class HBServerDriver implements HeartBeatDriver {
	private final ConsumerConnector consumer;
	private final ConsumerConfig config;
	private HBContent HBcontent;
	private CheckThread checkloop;
	private listenThread listenloop;
	public HBServerDriver() {
		config = configContent.getInstance().ConsumerConfig();
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		HBcontent = contentFactory.createHBC();
	}
	
	public void start(){
		get();
		put();
	}
	
	public void put() {
		// TODO Auto-generated method stub
		// check Drivers' status Loop
		// Daemon Thread
		checkloop = new CheckThread();
		checkloop.start();
		System.out.println("check loop started.");
//		checkloop.setDaemon(true);
	}

	public void get() {
		// TODO Auto-generated method stub
		// listen heart beat loop
		// Daemon Thread
		listenloop = new listenThread();
		listenloop.start();
		System.out.println("listen loop started.");
//		listenloop.setDaemon(true);
	}
	
	public String getTopic() {
		// TODO Auto-generated method stub
		return configContent.getInstance().getHBTopic();
	}

	public String getStatus() {
		// TODO Auto-generated method stub
//		String ret = "checkLoop:" + checkloop.isAlive() +"\nlistenLoop:" + listenloop.isAlive();
		return "alive";
	}
	
	class CheckThread extends Thread {

		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				HBcontent.check();
				try {
					Thread.sleep(configContent.getInstance().getTimeOutInterval());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HBcontent.getStatus();
			}
		}	
	}
	
	class listenThread extends Thread {
		
		public void run() {
			// TODO Auto-generated method stub
			task();
		}
		private void task() {
			// TODO Auto-generated method stub
			Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	        topicCountMap.put(configContent.getInstance().getHBTopic(), new Integer(1));

	        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
	        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

	        Map<String, List<KafkaStream<String, String>>> consumerMap = 
	                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
	        KafkaStream<String, String> stream = consumerMap.get(configContent.getInstance().getHBTopic()).get(0);
	        ConsumerIterator<String, String> it = stream.iterator();
	        while (it.hasNext()){
	        	String msg = it.next().message();
//	        	System.out.println(msg);
	        	HeartBeatData data = new HeartBeatData(msg);
	        	HBcontent.active(data);
	        }
		}
		
	}
}
