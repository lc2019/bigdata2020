package core.rdds.exchange_rdd.kv

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object cogroup {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val init = List((6, "c"), (5, "a"), (4, "c"))

    val irdd: RDD[(Int, String)] = sc.makeRDD(init)
    // TODO: 即使key没有也可以列出来 分组连接
    val ardd: RDD[(Int, Int)] = sc.makeRDD(Array((4, 4), (2, 5), (3, 6)))
    irdd.cogroup(ardd).collect().foreach(println)
  }

}
