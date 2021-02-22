package wc_demo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object demo1 {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val rdd1 = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 3)

    val rdd2: RDD[Int] = rdd1.map(_ * 10)
    //    val func = (e: Int) => e % 2
    //    val rdd2 = new MapPartitionsRDD[Int, Int](rdd1, (_, _, iterator) => iterator.map(func))
    rdd2.collect().foreach(println)
  }
}
