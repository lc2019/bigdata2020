package doit.pro

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 连续登陆处理流程
 * guid01,2018-02-28
 * guid01,2018-03-01
 * guid01,2018-03-01
 * (guid02,3,2018-03-01,2018-03-03)最终结果
 * 核心就是数据插入和日期减除
 */
object lianxudenglu {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val lines: RDD[String] = sc.textFile("datas/data1.txt")
    // 1.数据切分去重，结构转换->  (uid,dt)  -
    val grdd: RDD[(String, Iterable[String])] = lines.map(item => {
      val fields: Array[String] = item.split(",")
      val uid: String = fields(0)
      val dt: String = fields(1)
      (uid, dt)
    }).distinct().groupByKey() //去重加分组

    //2.排序
    val frdd: RDD[(String, (String, String))] = grdd.flatMapValues(iter => {
      //转为list进行排序
      val sorted: List[String] = iter.toList.sorted
      var num = 0
      //格式转换
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val calendar: Calendar = Calendar.getInstance()
      sorted.map(
        dt => {
          val date: Date = sdf.parse(dt)
          //当前时间作为传入时间
          calendar.setTime(date)
          //减法
          calendar.add(Calendar.DATE, -num)
          //再次转换时间
          val timeDif: Date = calendar.getTime
          val dateDif: String = sdf.format(timeDif)
          num += 1
          //(guid03,(2018-03-11,5,2018-03-07))
          //          (dt, num, dateDif)
          (dt, dateDif)
        }
      )
    })
   //3.结果转换聚合，过滤大于等于3天 然后在进行转换
    val res: RDD[(String, Int, String, String)] = frdd.map {
      case (uid, (dt, dateDif)) => {
        //（（uid date） ，（1 起始时间 结束时间））
        ((uid, dateDif), (1, dt, dt))
      }
    }.reduceByKey((v1, v2) => {
      //(uid ,(3,2018-03-01,2018-03-01))
//     对值进行比较22数据格式 (次数sum, dt, dt)
      (v1._1 + v2._1, Ordering[String].min(v1._2,v2._2),Ordering[String].max(v1._2,v2._2))
    }).filter(_._2._1>=3).map{
      case ((uid ,dt),(num,st,et)) =>{
        (uid,num,st,et) //(guid02,3,2018-03-01,2018-03-03)
      }
    }
    res.collect().foreach(println)
  }
}
