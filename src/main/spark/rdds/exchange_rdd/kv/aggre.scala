package rdds.exchange_rdd.kv

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// TODO: 分组聚合 支持 分区内 分区间的方法不一致
/**
 * 第一个参数是分区内，第二个分区间
 */

object aggre {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区
    val initRdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 6), ("a", 6)), 2)
    // TODO: 初始值 分区内方法最大，分区间相加
    //    val resrdd: RDD[(String, Int)] = initRdd.aggregateByKey(0)(math.max(_, _), _ + _)
    // TODO: 分区内相加 分区间相加
    val resrdd2: RDD[(String, Int)] = initRdd.aggregateByKey(0)(_ + _, _ - _)
    //    resrdd.collect().foreach(println)
    resrdd2.collect().foreach(println)
  }

}
