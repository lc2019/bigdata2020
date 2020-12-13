package com.jld.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class producer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //创建配置对象
        Properties conf = new Properties();
        //集群配置
        conf.setProperty("bootstrap.servers", "hadoop102:9092");
        //key 序列化
        conf.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //value 序列化
        conf.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //应答机制
        conf.setProperty("acks", "1");
        //创建生产者-KafkaProducer生产消息
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(conf);

        //准备数据
        String topics = "hello";
        String value = "hello kafka";
        for (int i = 0; i < 10; i++) {
            //消息记录 主题+数据
            ProducerRecord record = new ProducerRecord(topics, value);
            //生产数据
//            producer.send(record).get();
            //异步发送消息
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        String topic = recordMetadata.topic();
                        int partition = recordMetadata.partition();
                        long offset = recordMetadata.offset();
                        System.out.println(topic + " " + partition + " " + offset);
                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
        //关闭
        producer.close();
    }
}
