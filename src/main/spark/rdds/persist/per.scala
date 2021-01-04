package rdds.persist

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object per {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
    //切分
    val frdd: RDD[String] = rdd.flatMap(_.split(" "))
    //结构转换
    val mrdd: RDD[(String, Int)] = frdd.map((_, 1))
    //聚合
    val res: RDD[(String, Int)] = mrdd.reduceByKey(_ + _)
    res.collect().foreach(println)

    val frdd2: RDD[String] = rdd.flatMap(_.split(" "))
    //结构转换
    val mrdd2: RDD[(String, Int)] = frdd2.map((_, 1))
    //聚合
    val res2: RDD[(String, Iterable[Int])] = mrdd2.groupByKey()
    res2.collect().foreach(println)
  }
}
