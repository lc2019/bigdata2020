package core.pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.StringOps
import scala.collection.mutable

// TODO: 优化 
object demo5 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    //1.读取原始日志
    val ardd: RDD[String] = sc.textFile("datas")
    // TODO: 重复使用的rdd使用缓存 
    ardd.cache()

    val t10: Array[(String)] = top10Cat(ardd)
    //1.过滤，保留点击和前10的品类
    val frdd: RDD[String] = ardd.filter(
      action => {
        val datas: Array[String] = action.split("_")
        if (datas(6) != "-1") {
          t10.contains(datas(6))
        } else {
          false
        }
      }
    )

    //2.根据品类和session点击量进行统计
    val reducerdd: RDD[((String, String), Int)] = frdd.map(
      action => {
        val datas: Array[String] = action.split("_")
        ((datas(6), datas(2)), 1)
      }
    ).reduceByKey(_ + _)

    //3.统计结果进行转换
    val maprdd: RDD[(String, (String, Int))] = reducerdd.map {
      case ((cid, sid), sum) => {
        (cid, (sid, sum))
      }
    }
    //4.相同品类进行聚合
    val grdd: RDD[(String, Iterable[(String, Int)])] = maprdd.groupByKey()
    //5.将分组结果的数据进行点击量的排序，取前10
    val res: RDD[(String, List[(String, Int)])] = grdd.mapValues(
      iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(10)
      }
    )
    res.collect().foreach(println)
  }

  // TODO: top10的方法
  def top10Cat(ardd: RDD[String]): Array[String] = {
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

    anys.sortBy(_._2, false).take(10).map(_._1)
  }
}
