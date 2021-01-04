package rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// TODO: 分区内分区间的方法一致  分组聚合 
object reducebykey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val prdd: RDD[String] = sc.makeRDD(List("one", "two", "two", "three", "three", "three"))
    val nrdd: RDD[(String, Int)] = prdd.map(w => (w, 1))
    // TODO: key只出现一次的时候 不参与计算  支持预聚合 减少shuffle落盘的数据量
    val res: RDD[(String, Int)] = nrdd.reduceByKey(_ + _)
    res.collect().foreach(println)
  }
}
