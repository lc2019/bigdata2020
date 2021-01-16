package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object join {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(2000))
    context.checkpoint("cp")

    //获取端口数据
    val lines: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)
    val lines2: ReceiverInputDStream[String] = context.socketTextStream("localhost", 8888)

    val m9: DStream[(String, Int)] = lines.map((_, 9))
    val m8: DStream[(String, Int)] = lines2.map((_, 8))

    val jds: DStream[(String, (Int, Int))] = m9.join(m8)
    jds.print()
    //启动采集及
    context.start()
    //等待采集器关闭
    context.awaitTermination()
  }

}
