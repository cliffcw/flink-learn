package com.chenwan.flink.source;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: flink-learn
 * @description: KafkaUtils
 * @author: cliffcw
 * @create: 2019-06-13 13:59
 */
public class KafkaUtils {
    public static final String broker_list = "localhost:9092";
    public static final String topic = "topic-srvMonitor";

    public static void writeToKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //key 序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //value 序列化
        KafkaProducer producer = new KafkaProducer<String, String>(props);

        Metric metric = new Metric();

        metric.setTimestamp(System.currentTimeMillis());
        metric.setName("mem");

        Map<String, String> tags = new HashMap<>();
        Map<String, Object> fields = new HashMap<>();

        tags.put("cluster", "cw");
        tags.put("host_ip", "172.16.141.32");

        fields.put("used_percent", 90d);
        fields.put("max", 27244873d);
        fields.put("used", 17244873d);
        fields.put("init", 27244873d);

        metric.setTags(tags);
        metric.setFields(fields);

        ProducerRecord record = new ProducerRecord<String, String>(topic, null, null, JSON.toJSONString(metric));
        producer.send(record);
        System.out.println("发送数据: " + JSON.toJSONString(metric));
        producer.flush();

    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(300);
            writeToKafka();
        }
    }
}
