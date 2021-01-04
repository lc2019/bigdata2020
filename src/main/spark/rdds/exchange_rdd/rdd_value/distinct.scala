package rdds.exchange_rdd.rdd_value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object distinct {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 1, 8, 5, 8, 9))
    //重新指定分区
    // TODO:  map(x => (x, null)).reduceByKey((x, y) => x, numPartitions).map(_._1)
    val drdd: RDD[Int] = srdd.distinct(2)
    drdd.saveAsTextFile("out")

    drdd.collect().foreach(println)

  }
}
