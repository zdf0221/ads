package wu.leizi.test;

import wu.leizi.Driver.TrackerLog.Flume2KafkaDriver;

public class KafkaConsumer {
    public static void main(String[] args) {
        new Flume2KafkaDriver().get();
    }
}