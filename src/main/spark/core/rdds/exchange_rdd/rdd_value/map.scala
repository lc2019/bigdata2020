package core.rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object map {
  def main(args: Array[String]): Unit = {
    // TODO: driver
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    // TODO: executor
    val res: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    //参数{}（）省  ，有几个分区执行几次
    val maprdd: RDD[Int] = res.map(
      i => {
        println("---------->" + i)
        i
      }
    )
    maprdd.collect()

  }
}
