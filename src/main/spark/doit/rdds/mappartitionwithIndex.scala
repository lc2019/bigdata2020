package doit.rdds

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object mappartitionwithIndex {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 3)
    //有分区编号 输入迭代器返回迭代器
    val rdd2: RDD[String] = rdd1.mapPartitionsWithIndex((index, iter) => {
//      val ints: Iterator[Int] = iter.map(_ * 10)
//      ints
      iter.map(e=>s"part $index value:$e")
    })
    rdd2.collect().foreach(println)
    sc.stop()
  }
}
