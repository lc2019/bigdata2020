package core.rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// TODO: 分组  聚合需要使用单独的map 
object groupbykey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val prdd: RDD[String] = sc.makeRDD(List("one", "two", "two", "three", "three", "three"))
    val nrdd: RDD[(String, Int)] = prdd.map(w => (w, 1))
    // TODO: 相同的key分在一个元组中，形成一个元组 元组第一个元素就是key 第二个就是key的value的集合
    //只能分组 不能聚合
    val tuples: Array[(String, Int)] = nrdd.groupByKey().collect().map(t => (t._1, t._2.sum))
    println(tuples.mkString(","))
    //按key进行分组
    nrdd.groupByKey().collect().foreach(println)
  }
}
