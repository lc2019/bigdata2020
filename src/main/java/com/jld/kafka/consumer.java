package com.jld.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 消费程序
 */
public class consumer {
    public static void main(String[] args) {
        //创建配置对象
        Properties conf = new Properties();
        //集群配置
        conf.setProperty("bootstrap.servers", "hadoop102:9092");
        //消费者组
        conf.setProperty("group.id", "test");
        //自动提交
        conf.setProperty("enable.auto.commit", "true");
        //自动提交时间间隔
        conf.setProperty("auto.commit.interval.ms", "1000");
        //key,value 反序列化
        conf.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        conf.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //kafka的消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(conf);
        //发布订阅
        consumer.subscribe(Arrays.asList("hello"));

        //批量拉取数据
        while (true) {
            ConsumerRecords<String, String> polls = consumer.poll(5);
            for (ConsumerRecord<String, String> poll : polls) {
                //打印消息的详细信息
                String topic = poll.topic();
                long offset = poll.offset();
                String key = poll.key();
                String value = poll.value();
                System.out.println("topic: " + topic + "offset: " + offset + "key: " + key + "value: " + value);
            }
        }
    }
}
