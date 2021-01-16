package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object window {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(5))

    //获取端口数据
    val lines: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)
    val ds: DStream[(String, Int)] = lines.map((_, 1))
    //为了避免重复数据可以改变滑动的步长,（滑动窗口时长滑动窗口间隔）  -->计算内容的范围，隔多久触发一次计算
    val wds: DStream[(String, Int)] = ds.window(Seconds(10), Seconds(10))
    /**
     * 7个d分布在2个窗口
     */
    //    Time: 1609905195000 ms
    //      -------------------------------------------
    //    (d,1)
    //
    //    -------------------------------------------
    //    Time: 1609905205000 ms
    //      -------------------------------------------
    //    (d,6)
    val res: DStream[(String, Int)] = wds.reduceByKey(_ + _)
    res.print()
    //启动采集及
    context.start()
    //等待采集器关闭
    context.awaitTermination()
  }

}
