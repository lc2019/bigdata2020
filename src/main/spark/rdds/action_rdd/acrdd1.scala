package rdds.action_rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object acrdd1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象 
    val sc = new SparkContext(conf)
    // TODO: 行动算子出发job执行的方法 runjob

    val irdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val i: Int = irdd.reduce(_ + _)
    println(irdd.count())
    println(irdd.take(2).mkString(","))
    println(i)

    println(irdd.aggregate(0)(_ + _, _ + _))
    println(irdd.fold(0)(_ + _))
    sc.stop()
  }
}
