package rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object sortby {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    val fr: RDD[String] = sc.makeRDD(List("lc", "2020", "007", "biji", "wanshua", "ll"))
    //不能使用——代替 不知道是参数还是函数  升序降序
    val flrdd: RDD[String] = fr.sortBy(x => x, false)
    flrdd.collect().foreach(println)
  }
}
