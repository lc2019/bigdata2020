package rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object mapvalues {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val initRdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
    // TODO: 初始值 分区内 分区间方法一致

    // TODO: 只对v进行操作 (a,4)
    val res: RDD[(String, Int)] = initRdd.mapValues(_ + 1)

    res.collect().foreach(println)
  }

}
