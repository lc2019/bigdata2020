package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object rdd2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)
    /*
    1,2
    3,4,5
    6,7,8
     */
    val listrdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8), 3)
    // TODO: 将一个分区的数据放到一个数组中
    val grdd: RDD[Array[Int]] = listrdd.glom()
    grdd.collect().foreach(array => {
      println(array.mkString(","))
    })
  }
}
