package rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object golm {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    val irdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val frdd: RDD[Array[Int]] = irdd.glom()
    val sum: Int = frdd.map(
      // TODO: array求分区数组最大，最大和
      array => {
        array.max
      }
    ).collect().sum
    println(sum)
  }
}
