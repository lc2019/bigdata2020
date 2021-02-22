package fuxi.rdd.trans.value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object sample {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)
    val srdd : RDD[Int] = sc.makeRDD(List(1, 2, 3, 4,5,6,7,8,9,10))
    //是否放回，抽样的几率 随机数种子默认是系统时间
    val res: RDD[Int] = srdd.sample(false, 0.5)
    println(res.collect().mkString(","))

  }
}
