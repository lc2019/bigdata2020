package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object rdd_map {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    val maprdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    //    val m2: RDD[Int] = maprdd.map(_*2)


    //    // TODO: 对一个分区的所有数据进行计算 效率优于map算子 减少交互次数
    //    val m2: RDD[Int] = maprdd.mapPartitions(datas => {
    //      datas.map(_ * 2)
    //    })
    //
    //    m2.collect().foreach(println)

    val tprdd: RDD[(Int, String)] = maprdd.mapPartitionsWithIndex {
      case (num, datas) => {
        datas.map((_, "分区" + num))
      }
    }
    tprdd.collect().foreach(println)
  }
}
