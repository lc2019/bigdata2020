package core.rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object sortby {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val init = List((6, "c"), (5, "a"), (4, "c"))
    val irdd: RDD[(Int, String)] = sc.makeRDD(init)
    // TODO: 升序降序
    irdd.sortByKey(false).collect().foreach(println)
  }

}
