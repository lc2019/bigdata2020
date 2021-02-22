package doit.rdds

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object keysvalues {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(Array(("spark", 1), ("hadoop", 2), ("hive", 3), ("2021", 4)),3)
    rdd1.keys.collect().foreach(println)
    rdd1.values.collect().foreach(println)
  }
}
