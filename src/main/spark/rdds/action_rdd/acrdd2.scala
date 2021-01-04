package rdds.action_rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object acrdd2 {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val initRdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
    // TODO: key出现的次数
    val rdd: collection.Map[String, Long] = initRdd.countByKey()

    val rdds: collection.Map[(String, Int), Long] = initRdd.countByValue()
    println(rdds)

  }

}
