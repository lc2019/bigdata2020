package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object grouprdd {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)
    //    val gprdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    //    TODO: 每个数据进行分组，根据返回的key进行分组 相同的key放置在一个组
    //    val gpr: RDD[(Int, Iterable[Int])] = gprdd.groupBy(i => i % 2)
    val irdd: RDD[String] = sc.makeRDD(List("hello", "spark", "hi", "scala"))
    val gpr: RDD[(Char, Iterable[String])] = irdd.groupBy(_.charAt(0))
    gpr.collect().foreach(println)
  }
}
