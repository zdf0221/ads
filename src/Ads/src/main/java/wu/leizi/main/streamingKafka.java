package wu.leizi.main;


import java.util.Map;
import java.util.HashMap;

import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

public class streamingKafka {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SparkConf sparkConf = new SparkConf().setAppName("JavaKafkaWordCount").setMaster("local[2]");
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(1000));
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put("HEARTBEAT-TOPIC", 2);
		JavaPairReceiverInputDStream<String, String> messages =
	            KafkaUtils.createStream(jssc, "127.0.0.1:2181", "jd-group", topicMap);
		JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
		      public String call(Tuple2<String, String> tuple2) {
		        return tuple2._2();
		      }
		});
		lines.print();
		jssc.start();
	    jssc.awaitTermination();
	}

}
