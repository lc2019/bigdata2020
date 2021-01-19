package fuxi.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object make {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)
    //内存创建
    val mrdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    //外部存储创建
    val frdd: RDD[String] = sc.textFile("input")
    frdd.collect().foreach(println)

  }

}
