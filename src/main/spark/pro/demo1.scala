package pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.StringOps
import scala.collection.mutable

object demo1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    //1.读取原始日志
    val ardd: RDD[String] = sc.textFile("datas")
    //2.统计品类的点击率
    // TODO: 过滤
    val ck: RDD[String] = ardd.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(6) != "-1"
      }
    )
    // TODO: 格式转换
    val clickCount: RDD[(String, Int)] = ck.map(
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
    val cogroup: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = clickCount.cogroup(orderCount, payCount)
    val anys: RDD[(String, (Int, Int, Int))] = cogroup.mapValues {
      case (cli, ori, payi) => {
        var clickC = 0
        val iter1: Iterator[Int] = cli.iterator
        if (iter1.hasNext) {
          clickC = iter1.next()
        }
        var orderC = 0
        val iter2: Iterator[Int] = ori.iterator
        if (iter2.hasNext) {
          orderC = iter2.next()
        }
        var payC = 0
        val iter3: Iterator[Int] = payi.iterator
        if (iter3.hasNext) {
          payC = iter3.next()
        }
        (clickC, orderC, payC)
      }

    }
    val res = anys.sortBy(_._2, false).take(10)
    //打印
    res.foreach(println)
  }
}
