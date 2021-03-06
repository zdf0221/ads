package wu.leizi.config;

import java.util.Properties;

import kafka.consumer.ConsumerConfig;

public class kafkaConfig implements config {
	private Properties flumeProps;
	private static kafkaConfig instance = null;
	private String LogTopic;
	private kafkaConfig() {
		LogTopic = "LOG-TRACKER-TOPIC";
		flumeProps = new Properties();
        //zookeeper 配置
		flumeProps.put("zookeeper.connect", "127.0.0.1:2181");

        //group 代表一个消费组
		flumeProps.put("group.id", "jd-group");
        //zk连接超时
		flumeProps.put("zookeeper.session.timeout.ms", "4000");
		flumeProps.put("zookeeper.sync.time.ms", "200");
		flumeProps.put("auto.commit.interval.ms", "1000");
		flumeProps.put("auto.offset.reset", "smallest");
        //序列化类
		flumeProps.put("serializer.class", "kafka.serializer.StringEncoder");
	}
	
	public String getTrackerTopic() {
		return LogTopic;
	}
	
	public ConsumerConfig flumeConfig() {
		return new ConsumerConfig(flumeProps);
	}
	
	public static kafkaConfig getInstance() {
		if (instance == null) instance = new kafkaConfig();
		return instance;
	}
	
	
}
