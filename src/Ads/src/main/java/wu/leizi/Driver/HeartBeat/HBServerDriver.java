package wu.leizi.Driver.HeartBeat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import wu.leizi.Content.HBContent;
import wu.leizi.config.HBConfig;
import wu.leizi.data.HeartBeatData;

public class HBServerDriver implements HeartBeatDriver {
	private final ConsumerConnector consumer;
	private final ConsumerConfig config;
	private HBContent HBcontent;
	private Thread checkloop;
	private Thread listenloop;
	public HBServerDriver(HBContent hb) {
		config = HBConfig.getInstance().ConsumerConfig();
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		HBcontent = hb;
	}
	
	public void start(){
		get();
		put();
	}
	
	public void put() {
		// TODO Auto-generated method stub
		// check Drivers' status Loop
		// Daemon Thread
		checkloop = new Thread(new CheckThread());
		checkloop.start();
//		System.out.println("check loop started.");
//		checkloop.setDaemon(true);
	}

	public void get() {
		// TODO Auto-generated method stub
		// listen heart beat loop
		// Daemon Thread
		listenloop = new Thread(new listenThread());
		listenloop.start();
	}
	
	public String getTopic() {
		// TODO Auto-generated method stub
		return HBConfig.getInstance().getHBTopic();
	}

	public String getStatus() {
		// TODO Auto-generated method stub
//		String ret = "checkLoop:" + checkloop.isAlive() +"\nlistenLoop:" + listenloop.isAlive();
		return "alive";
	}
	
	class CheckThread implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				HBcontent.check();
				try {
					Thread.sleep(HBConfig.getInstance().getTimeOutInterval());
					HBcontent.getStatus();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	
	@Override
	public HashMap<String, String> get(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class listenThread implements Runnable {
		
		public void run() {
			// TODO Auto-generated method stub
			task();
		}
		private void task() {
			// TODO Auto-generated method stub
			Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	        topicCountMap.put(HBConfig.getInstance().getHBTopic(), new Integer(1));

	        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
	        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

	        Map<String, List<KafkaStream<String, String>>> consumerMap = 
	                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
	        KafkaStream<String, String> stream = consumerMap.get(HBConfig.getInstance().getHBTopic()).get(0);
	        ConsumerIterator<String, String> it = stream.iterator();
	        while (it.hasNext()){
	        	String msg = it.next().message();
	        	HeartBeatData data = new HeartBeatData(msg);
	        	try {
					HBcontent.active(data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		
	}
}
