package core.rdds.exchange_rdd.exsample

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//统计每个省份每个广告被点击的top3


object obj2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象 
    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.textFile("in/agent.log")
    //  时间戳        省份 城市 用户  广告
    // 1516609143867  6    7   64    16
    // （（省份 广告）， 点击）===>（省份，（广告，点击））===>排序
    val irdd: RDD[((String, String), Int)] = rdd.map(
      line => {
        val datas: Array[String] = line.split(" ")
        //（省份 广告）， 点击）
        ((datas(1), datas(4)), 1)

      }
    )
    // TODO:  按key进行分组
    val srdd: RDD[((String, String), Int)] = irdd.reduceByKey(_ + _)
    //（（省份 广告）， 点击）===>（省份，（广告，点击））
    val frdd: RDD[(String, (String, Int))] = srdd.map {
      // TODO: 使用模式匹配 
      //      x => {
      //        (x._1._1, (x._1._2, x._2))
      case ((prv, ad), sum) => {
        (prv, (ad, sum))
      }
    }

    val rdds: RDD[(String, Iterable[(String, Int)])] = frdd.groupByKey()
    val firdd: RDD[(String, List[(String, Int)])] = rdds.mapValues(
      i => {
        i.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
      }
    )
    firdd.collect().foreach(println)
  }
}
