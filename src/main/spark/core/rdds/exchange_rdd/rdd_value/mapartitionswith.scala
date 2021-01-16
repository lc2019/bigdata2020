package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object mapartitionswith {
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
    //    val maprdd: RDD[Int] = res.mapPartitionsWithIndex(
    //  // TODO: 分区索引 迭代器
    ////      (index, iter) => {
    ////        //获取1号分区的数据
    ////        if (index == 1) {
    ////          iter
    ////        } else {
    ////          Nil.iterator
    ////        }
    ////      }
    ////    )
    val maprdd: RDD[(Int, Int)] = res.mapPartitionsWithIndex(

      (index, iter) => {
        // TODO: 数据转换格式
        iter.map(
          x => {
            (index, x)
          }
        )

      }
    )

    maprdd.collect().foreach(println)

  }
}
