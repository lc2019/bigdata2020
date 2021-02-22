package doit.pro

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * uid,start_time,end_time,flow
 * 1,2020-02-18 16:06:27,2020-02-18 17:20:49,50
 * 1,2020-02-18 17:21:50,2020-02-18 18:03:27,60
 * 2,2020-02-18 14:18:24,2020-02-18 15:01:40,20
 */
object liuliang {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val lines: RDD[String] = sc.textFile("datas/data.csv")

    lines.mapPartitions(iter => {
      val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      iter.map(
        it => {
          val fields: Array[String] = it.split(",")
          // uid,start_time,end_time,flow
          val uid: String = fields(0)
          val startTime: Long = sdf.parse(fields(1)).getTime
          val endTime: Long = sdf.parse(fields(2)).getTime
          val downFlow: Long = fields(3).toLong
          (uid, (startTime, endTime, downFlow))
        }
      )
    }).groupByKey().flatMapValues(iter => {
      val sorted: List[(Long, Long, Long)] = iter.toList.sortBy(_._1)
      var tmp = 0L
      var flg = 0
      var sum = 0
      //起始时间，结束时间，流量
      sorted.map(
        e => {
          val st = e._1
          val et = e._2
          val flow = e._3
          if (tmp != 0) {
            //合并元则  上网间隔时间不超过10分钟
            if ((st - tmp) / (1000 * 60) > 10) flg = 1 else flg = 0
          }
          tmp = et
          sum += flg
          (st, et, flow, sum)
        }
      )
    }).map {
      case (uid, (st, et, flow, sum)) => {
        //
        ((uid, sum), (flow, st, et))
      }
    }.reduceByKey((a, b) => {
      (a._1 + b._1, math.min(a._2, b._2), math.max(a._3, b._3))
    }).mapPartitions(
      it => {
        val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        it.map {
          case ((uid, _), (flow, st, et)) => {
            (uid, sdf.format(new Date(st)), sdf.format(new Date(et)), flow)
          }
        }
      }
    ).collect().foreach(println)
  }
}
