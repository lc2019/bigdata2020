package rdds.persist

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object per2 {
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
    //    mrdd.cache() 默认保存在内存 临时文件
    //    mrdd.persist(StorageLevel.DISK_ONLY)
    //磁盘路径永久文件
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
