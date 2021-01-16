package core.rdds.exchange_rdd.rdd_value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object sample {
  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd: RDD[Int] = sc.makeRDD(1 to 10)
    // TODO: 抽样
    val sprdd: RDD[Int] = srdd.sample(false, 0.5)
    sprdd.collect().foreach(println)
  }
}
