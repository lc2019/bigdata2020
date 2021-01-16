package stream.kafka

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

// TODO: 消费kafka数据
object kafka_op {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(3))
    val kafkaPara: Map[String, Object] = Map[String, Object](ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG ->
      "hadoop101:9092,hadoop102:9092,hadoop103:9092", ConsumerConfig.GROUP_ID_CONFIG -> "atguigu", "key.deserializer" ->
      "org.apache.kafka.common.serialization.StringDeserializer", "value.deserializer" ->
      "org.apache.kafka.common.serialization.StringDeserializer"
    )
    val kds: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      context,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Set("atguigu"), kafkaPara))

    kds.map(_.value()).print()
    context.start()
    context.awaitTermination()

  }

}
