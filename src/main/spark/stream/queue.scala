package stream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object queue {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")
    val context = new StreamingContext(conf, Seconds(3))

    val rddQueue = new mutable.Queue[RDD[Int]]()
    val in: InputDStream[Int] = context.queueStream(rddQueue, oneAtATime = false)
    val maprdd: DStream[(Int, Int)] = in.map((_, 1))
    val rerdd: DStream[(Int, Int)] = maprdd.reduceByKey(_ + _)
    rerdd.print()

    context.start()
    for (elem <- 1 to 5) {
      rddQueue += context.sparkContext.makeRDD(1 to 300, 10)
      Thread.sleep(2000)
    }


  }

}
