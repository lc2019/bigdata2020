package rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// TODO: 调用的就是coalesce、 默认shuffle
object repartition {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd: RDD[Int] = sc.makeRDD(1 to 16, 4)

    println(srdd.partitions.size)

    // TODO: 缩减分区 会把后面的分区合并到前面的分区
    val crdd: RDD[Int] = srdd.repartition(2)
    crdd.saveAsTextFile("out1")
    println(crdd.partitions.size)

  }
}
