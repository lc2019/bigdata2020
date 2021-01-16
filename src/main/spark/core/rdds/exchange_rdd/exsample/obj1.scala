package core.rdds.exchange_rdd.exsample

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object obj1 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象 
    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.textFile("in/apache.log")
    // TODO: 按小时统计点击 
    val res: RDD[(String, Iterable[(String, Int)])] = rdd.map(
      line => {
        val datas: Array[String] = line.split(" ")
        val time: String = datas(3)
        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date: Date = sdf.parse(time)
        val sdf1 = new SimpleDateFormat("HH")
        val hour: String = sdf1.format(date)
        (hour, 1)
      }
    ).groupBy(_._1)

    //数据处理
    // 83.149.9.216 - - 17/05/2015:10:05:03 +0000 GET /presentations/logstash-monitorama-2013/images/kibana-search.png
    res.map {
      case (hour, iter) => {
        (hour, iter.size)
      }
    }.collect().foreach(println)
  }
}
