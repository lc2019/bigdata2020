package fuxi.rdd.trans.value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object filter {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)
    val frdd : RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val res: RDD[Int] = frdd.filter(_ % 2 == 0)
    res.collect().foreach(println)

  }
}
