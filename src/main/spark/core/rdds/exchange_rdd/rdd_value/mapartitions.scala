package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object mapartitions {
  def main(args: Array[String]): Unit = {
    // TODO: driver
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    // TODO: executor
    val res: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    //分区为单位进行数据处理，有几个分区执行几次
    //    val maprdd: RDD[Int] = res.mapPartitions(i => {
    //      println("#####")
    //      i.map(_ * 2)
    //    })
    // TODO: 2个分区的最大值
    val maprdd: RDD[Int] = res.mapPartitions(
      iter => {
        // TODO: 迭代器的最大值封装进list在进行迭代
        List(iter.max).iterator
      }
    )
    maprdd.collect()

  }
}
