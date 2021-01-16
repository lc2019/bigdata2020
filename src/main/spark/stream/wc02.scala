package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wc02 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(2))
    context.checkpoint("cp")

    //获取端口数据
    val lines: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)
    val words: DStream[String] = lines.flatMap(_.split(" "))
    val wc: DStream[(String, Int)] = words.map((_, 1))
    // TODO: 有状态的计算 根据key对状态进行更新
    //第一个值相同key的value值
    //第二个缓冲区相同的key的value值
    //    val res: DStream[(String, Int)] = count.reduceByKey(_+_)
    val state: DStream[(String, Int)] = wc.updateStateByKey(
      (seq: Seq[Int], buff: Option[Int]) => {
        val count: Int = buff.getOrElse(0) + seq.sum
        Option(count)
      }
    )
    state.print()


    //启动采集及
    context.start()
    //等待采集器关闭
    context.awaitTermination()
  }

}
