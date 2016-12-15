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
import wu.leizi.Demo.KafkaProducer;
import wu.leizi.moniterContent.configContent;

public class HBServerDriver implements HeartBeatDriver {
	private final ConsumerConnector consumer;
	private final ConsumerConfig config;
	public HBServerDriver() {
		config = configContent.getInstance().ConsumerConfig();
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
	}
	public void put() {
		// TODO Auto-generated method stub
		
	}

	public void get() {
		// TODO Auto-generated method stub
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(KafkaProducer.TOPIC, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap = 
                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(KafkaProducer.TOPIC).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext())
            System.out.println(it.next().message());
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
