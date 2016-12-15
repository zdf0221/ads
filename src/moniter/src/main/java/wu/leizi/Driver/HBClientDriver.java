package wu.leizi.Driver;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import wu.leizi.moniterContent.configContent;

public class HBClientDriver implements HeartBeatDriver {
	private final Producer<String, String> producer;
	public HBClientDriver() {
		producer = new Producer<String, String>(configContent.getInstance().ProduceConfig());
	}
	public void put() {
		// TODO Auto-generated method stub
		int messageNo = 1000;
        final int COUNT = 10000;

        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            producer.send(new KeyedMessage<String, String>(configContent.getInstance().getHBTopic(), key ,data));
            System.out.println(data);
            messageNo ++;
        }
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
		return null;
	}
	
	

}
