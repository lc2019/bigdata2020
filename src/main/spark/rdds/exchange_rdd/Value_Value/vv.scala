package rdds.exchange_rdd.Value_Value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object vv {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    val f1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val f2: RDD[Int] = sc.makeRDD(List(4, 5, 6, 7))
    //不能使用——代替 不知道是参数还是函数  升序降序
    // TODO: 并集
    val jj: RDD[Int] = f1.union(f2)
    println(jj.collect().mkString(","))
    // TODO: 交集
    val cj: RDD[Int] = f1.intersection(f2)
    println(cj.collect().mkString(","))
    // TODO: 差集
    val crdd: RDD[Int] = f1.subtract(f2)
    println(crdd.collect().mkString(","))
    // TODO: zip 数据源的类型可以不一致，但是分区数量要保持一致且分区元素要一致
    val zrdd: RDD[(Int, Int)] = f1.zip(f2)
    println(zrdd.collect().mkString(","))

  }
}
