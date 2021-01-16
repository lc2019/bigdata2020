package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.util.Random

object diy {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(3))

    val mess: ReceiverInputDStream[String] = context.receiverStream(new myReceiver)
    mess.print()

    context.start()
    context.awaitTermination()

  }

  class myReceiver extends Receiver[String](StorageLevel.MEMORY_ONLY) {
    private var flg = true

    override def onStart(): Unit = {
      new Thread(new Runnable {
        override def run(): Unit = {
          while (flg) {
            val mess: String = "采集的数据： " + new Random().nextInt(10).toString
            store(mess)
            Thread.sleep(500)
          }
        }
      }
      ).start()
    }

    override def onStop(): Unit = {
      flg = false
    }
  }

}
