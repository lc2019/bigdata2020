package core.rdds.persist

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// TODO: cache persist checkpoint区别
/**
 * cache 将数据临时存储在内存中
 * persist 将数据临时存储在磁盘中  作业运行完成后删除
 * checkpoint 将数据永久存储在磁盘中
 * 会独立执行作业 为了提高效率联合cache使用--先cache在checkpoint
 */
object per3 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    sc.setCheckpointDir("ck")
    val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
    //切分
    val frdd: RDD[String] = rdd.flatMap(_.split(" "))
    //结构转换
    val mrdd: RDD[(String, Int)] = frdd.map((_, 1))
    // TODO: 持久化
    mrdd.cache()
    mrdd.checkpoint()
    //聚合
    val res: RDD[(String, Int)] = mrdd.reduceByKey(_ + _)
    //在行动算子执行的时候执行
    res.collect().foreach(println)

    println("$$$$$$$$$$$$$$")
    val res2: RDD[(String, Iterable[Int])] = mrdd.groupByKey()
    res2.collect().foreach(println)
  }
}
