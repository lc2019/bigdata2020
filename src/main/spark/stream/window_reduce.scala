package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object window_reduce {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(5))
    // TODO: 只要保存流数据的状态就需要设置检查点 
    context.checkpoint("cp")

    //获取端口数据
    val lines: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)
    val ds: DStream[(String, Int)] = lines.map((_, 1))
    // TODO: 减去重复的窗口数据 
    val wds: DStream[(String, Int)] =
      ds.reduceByKeyAndWindow(
        (x: Int, y: Int) => {
          x + y
        },
        (x: Int, y: Int) => {
          x - y
        },
        Seconds(10), Seconds(5))


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

    wds.print()
    //启动采集及
    context.start()
    //等待采集器关闭
    context.awaitTermination()
  }

}
