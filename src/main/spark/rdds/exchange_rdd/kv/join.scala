package rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object join {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val init = List((6, "c"), (5, "a"), (4, "c"))

    val irdd: RDD[(Int, String)] = sc.makeRDD(init)
    // TODO: 笛卡尔积(4,(c,4)) 相同key的value 会形成一个元组
    val ardd: RDD[(Int, Int)] = sc.makeRDD(Array((4, 4), (2, 5), (3, 6)))
    irdd.join(ardd).collect().foreach(println)
  }

}
