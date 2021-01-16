package core.rdds.io

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// TODO:  读文件
//  textFile objectFile sequenceFile
object demo1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    val readrdd: RDD[String] = sc.textFile("out")
    println(readrdd.collect().mkString(","))

  }
}
