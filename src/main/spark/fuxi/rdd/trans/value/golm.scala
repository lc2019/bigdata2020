package fuxi.rdd.trans.value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object golm {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)
    
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)
    val grdd: RDD[Array[Int]] = rdd.glom()

    val max: RDD[Int] = grdd.map(
      arr => arr.max
    )
    // TODO: 分区取最大值然后求和 
    println(max.collect().sum)
  }
}
