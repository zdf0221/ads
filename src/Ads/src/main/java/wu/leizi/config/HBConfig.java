package wu.leizi.config;

import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.producer.ProducerConfig;

public class HBConfig implements config {
	private long HeartBeatInterval;
	private long TimeOutInterval;
	private Properties conProps;
	private Properties proProps;
	private String HeartBeatTopic;
	private int ServerWebPort;
	private static HBConfig instance = null;
	private HBConfig() {
		ServerWebPort = 8089;
		HeartBeatInterval = 300;
		TimeOutInterval = 1000;
		HeartBeatTopic = "HEARTBEAT-TOPIC";
		conProps = new Properties();
        //zookeeper 配置
		conProps.put("zookeeper.connect", "127.0.0.1:2181");

        //group 代表一个消费组
		conProps.put("group.id", "jd-group");
        //zk连接超时
		conProps.put("zookeeper.session.timeout.ms", "4000");
		conProps.put("zookeeper.sync.time.ms", "200");
		conProps.put("auto.commit.interval.ms", "1000");
		conProps.put("auto.offset.reset", "smallest");
        //序列化类
		conProps.put("serializer.class", "kafka.serializer.StringEncoder");
		
		
		proProps = new Properties();
        //此处配置的是kafka的端口
		proProps.put("metadata.broker.list", "127.0.0.1:9092");

        //配置value的序列化类
		proProps.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
		proProps.put("key.serializer.class", "kafka.serializer.StringEncoder");

        //request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
		proProps.put("request.required.acks","-1");
		
	}
	
	
	public static HBConfig getInstance() {
		if (instance == null) instance = new HBConfig();
		return instance;
	}
	
	public int getServerWebPort() {
		return ServerWebPort;
	}
	
	public long getHeartBeatInterval() {
		return HeartBeatInterval;
	}
	
	public long getTimeOutInterval() {
		return TimeOutInterval;
	}

	public boolean refresh(){
		return true;
	}
	
	public ConsumerConfig ConsumerConfig() {
		return new ConsumerConfig(conProps);
	}
	
	public ProducerConfig ProduceConfig() {
		return new ProducerConfig(proProps);
	}
	
	public String getHBTopic() {
		return HeartBeatTopic;
	}
}
