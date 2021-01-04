package rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object foldbykey {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val initRdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
    // TODO: 初始值 分区内 分区间方法相同

    // TODO: 相加
    val resrdd2: RDD[(String, Int)] = initRdd.foldByKey(0)(_ + _)

    resrdd2.collect().foreach(println)
  }

}
