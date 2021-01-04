package rdds.action_rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object wc2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    // TODO:  kv
    val irdd: RDD[(String, Int)] = words.map((_, 1))
    val frdd: RDD[(String, Iterable[Int])] = irdd.groupByKey()
    //iter[int]=>u
    val flrdd: RDD[(String, Int)] = frdd.mapValues(iter => {
      iter.size
    })
    println(flrdd.collect().mkString(","))

  }
}
