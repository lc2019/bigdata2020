package pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.StringOps
import scala.collection.mutable

// TODO: 优化 
object demo2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    //1.读取原始日志
    val ardd: RDD[String] = sc.textFile("datas")
    // TODO: 重复使用的rdd使用缓存 
    ardd.cache()
    //2.统计品类的点击率
    // TODO: 过滤
    val clickRdd: RDD[String] = ardd.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(6) != "-1"
      }
    )
    // TODO: 格式转换
    val clickCount: RDD[(String, Int)] = clickRdd.map(
      action => {
        val datas: Array[String] = action.split("_")
        (datas(6), 1)
      }
    ).reduceByKey(_ + _)

    //3.统计品类的下单数量
    val orderRdd: RDD[String] = ardd.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(8) != "null"
      }
    )
    //ooid 1,2,3
    val orderCount: RDD[(String, Int)] = orderRdd.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        val cids: StringOps = datas(8)
        val cid: mutable.ArrayOps[String] = cids.split(",")
        cid.map(id => (id, 1))
      }
    ).reduceByKey(_ + _)

    //4.统计品类的支付数量
    val payRdd: RDD[String] = ardd.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(10) != "null"
      }
    )
    val payCount: RDD[(String, Int)] = payRdd.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        val pids: StringOps = datas(10)
        val pid: mutable.ArrayOps[String] = pids.split(",")
        pid.map(
          id => (id, 1)
        )
      }
    ).reduceByKey(_ + _)
    //5.统计品类的top10-点击数量-下单数量-支付数量  （cid,(点击数量，下单数量，支付数量)）
    // TODO: cogroup可能存在shuffle
    // TODO: 数据格式转换  cid,cnt=>（cid,(cnt,0,0)）
    val rdd1: RDD[(String, (Int, Int, Int))] = clickCount.map {
      case (cid, cnt) => {
        (cid, (cnt, 0, 0)
        )
      }
    }

    val rdd2: RDD[(String, (Int, Int, Int))] = orderCount.map {
      case (cid, cnt) => {
        (cid, (0, cnt, 0)
        )
      }
    }
    val rdd3: RDD[(String, (Int, Int, Int))] = payCount.map {
      case (cid, cnt) => {
        (cid, (0, 0, cnt)
        )
      }
    }
    val srdd: RDD[(String, (Int, Int, Int))] = rdd1.union(rdd2).union(rdd3)
    val anys: RDD[(String, (Int, Int, Int))] = srdd.reduceByKey(
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
      }
    )

    val res = anys.sortBy(_._2, false).take(10)
    //打印
    res.foreach(println)
  }
}
