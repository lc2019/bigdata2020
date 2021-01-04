package rdds.action_rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object foreach {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
    // TODO:在driver执行
    rdd.collect().foreach(println)

    println("************")
    //在executor执行
    rdd.foreach(println)
    //为了区分不同的处理效果，rdd的方法叫做算子 rdd的方法可以将计算逻辑发送到executor执行

  }
}
