package stream.pro

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}


import scala.collection.mutable.ListBuffer
import scala.util.Random

// TODO: 向kafka中生产数据
object makeData {
  def main(args: Array[String]): Unit = {
    // 创建配置对象
    val prop = new Properties()
    // 添加配置
    prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop101:9092")
    prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](prop)

    while (true) {
      md().foreach(
        data => {
          //向kafaka中生成数据
          val record = new ProducerRecord[String, String]("atguigu", data)
          producer.send(record)
          //          println(data)
        }
      )
      Thread.sleep(2000)
    }
  }

  // time  区域 城市 用户id  广告id
  def md(): ListBuffer[String] = {
    val list: ListBuffer[String] = ListBuffer[String]()
    val areaList: ListBuffer[String] = ListBuffer[String]("华北", "华东", "华南")
    val cityList: ListBuffer[String] = ListBuffer[String]("北京", "上海", "深圳")
    // TODO: 循环生产数据
    for (i <- 1 to 30) {
      val area: String = areaList(new Random().nextInt(3))
      val city: String = cityList(new Random().nextInt(3))
      val userid = new Random().nextInt(6) + 1
      val adid = new Random().nextInt(6) + 1
      list.append(s"${System.currentTimeMillis()} $area $city $userid $adid")
    }
    list
  }
}
