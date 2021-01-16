package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object flatmap {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)
    // TODO: 集合的处理 
    //   val irdd: RDD[List[Int]] = sc.makeRDD(List(List(1,2), List(3,4)))
    //    val frdd: RDD[Int] = irdd.flatMap(
    //      list => list
    //    )
    //    frdd.collect().foreach(println)

    // TODO: 字符串处理 
    //    val srdd: RDD[String] = sc.makeRDD(List("hello scala", "hello world"))
    //    val frdd: RDD[String] = srdd.flatMap(x => x.split(" "))

    // TODO: 混合数据处理 
    val irdd: RDD[Any] = sc.makeRDD(List(List(1, 2), 3, List(4, 5)))
    val frdd: RDD[Any] = irdd.flatMap(
      data => {
        data match {
          case list: List[_] => list
          case dat => List(dat)
        }
      }
    )
    frdd.collect().foreach(println)
  }
}
