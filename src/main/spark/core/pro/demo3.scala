package core.pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.StringOps
import scala.collection.mutable

// TODO: 优化 
object demo3 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    //1.读取原始日志
    val ardd: RDD[String] = sc.textFile("datas")
    // TODO: 重复使用的rdd使用缓存 
    //2.直接将数据转换结构
    /**
     * 点击：品类id,(1,0,0)
     * 下单：品类id,(0,1,0)
     * 支付：品类id,(0,0,1)
     */
    val frdd: RDD[(String, (Int, Int, Int))] = ardd.flatMap(
      action => {
        val datas: mutable.ArrayOps[String] = action.split("_")
        if (datas(6) != "-1") {
          List((datas(6), (1, 0, 0)))
        } else if (datas(8) != "null") {
          var ids: Array[String] = datas(8).split(",")
          ids.map(id => (id, (0, 1, 0)))
        } else if (datas(10) != "null") {
          var ids: Array[String] = datas(10).split(",")
          ids.map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      }
    )
    //3.将相同的品类进行分组聚合
    val anys: RDD[(String, (Int, Int, Int))] = frdd.reduceByKey(
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
      }
    )

    val res = anys.sortBy(_._2, false).take(10)
    //打印
    res.foreach(println)
  }
}
