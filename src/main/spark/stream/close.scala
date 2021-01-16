package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext, StreamingContextState}

object close {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(3))

    //获取端口数据
    val lines: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)
    val words: DStream[String] = lines.flatMap(_.split(" "))
    val count: DStream[(String, Int)] = words.map((_, 1))

    count.print()

    //启动采集及
    context.start()
    // TODO: 关闭采集器 创建新线程
    new Thread(
      new Runnable {
        override def run(): Unit = {
          //计算节点不在接收新数据，而实将现有的数据处理完毕
          while (true) {
            if (true) {
              val state: StreamingContextState = context.getState()
              if (state == StreamingContextState.ACTIVE) {
                context.stop(true, true)
              }
            }
            Thread.sleep(5000)
          }
        }
      }
    ).start()
    //等待采集器关闭
    context.awaitTermination()
  }

}
