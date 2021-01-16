package core.rdds.exchange_rdd.rdd_value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object coalesce {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd: RDD[Int] = sc.makeRDD(1 to 16, 4)

    println(srdd.partitions.size)

    // TODO: 缩减分区 会把后面的分区合并到前面的分区,可以选择是否进行shuffle
    val crdd: RDD[Int] = srdd.coalesce(3)
    crdd.saveAsTextFile("out2")
    println(crdd.partitions.size)

  }
}
