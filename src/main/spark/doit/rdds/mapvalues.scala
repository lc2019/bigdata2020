package doit.rdds

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object mapvalues {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val rdd1: RDD[(String, String)] = sc.makeRDD(Array(("spark", "1,2,3,4"), ("hadoop", "2,3,4,5"), ("hive", "3,4,5"), ("2021", "4,5,6")),3)
//    rdd1.mapValues(x =>x*10).collect().foreach(println)
val res: RDD[(String, Int)] = rdd1.flatMapValues(_.split(",").map(_.toInt))
    res.collect().foreach(println)
  }
}
