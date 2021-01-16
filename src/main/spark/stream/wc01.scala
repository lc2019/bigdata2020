package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wc01 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(3))

    //获取端口数据
    val lines: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)
    val words: DStream[String] = lines.flatMap(_.split(" "))
    val count: DStream[(String, Int)] = words.map((_, 1))
    val res: DStream[(String, Int)] = count.reduceByKey(_ + _)
    res.print()

    //启动采集及
    context.start()
    //等待采集器关闭
    context.awaitTermination()
  }

}
