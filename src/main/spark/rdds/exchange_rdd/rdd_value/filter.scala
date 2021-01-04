package rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object filter {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    val fr: RDD[String] = sc.makeRDD(List("lc", "2020", "007", "biji", "wanshua"))
    //包含那个 如果包含打印
    val flrdd: RDD[String] = fr.filter(_.contains("wanshua"))
    flrdd.collect().foreach(println)
  }
}
