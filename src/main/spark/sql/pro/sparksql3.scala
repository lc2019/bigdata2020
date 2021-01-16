//package sql.pro
//
//import org.apache.log4j.{Level, Logger}
//import org.apache.spark.SparkConf
//import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
//import org.apache.spark.sql.expressions.Aggregator
//
//import scala.collection.mutable
//import scala.collection.mutable.ListBuffer
//
//// TODO: 自定义udaf
//object sparksql3 {
//  def main(args: Array[String]): Unit = {
//    Logger.getLogger("org").setLevel(Level.ERROR)
//    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
//    // TODO: 创建session
//    val session: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).config("hive.metastore.uris", "thrift://hadoop103:9083").getOrCreate()
//    // TODO: sparksql 连接外置的hive
//    session.sql("use myhive")
//    session.sql(
//      """
//        | select
//        |	a.*,
//        |	p.product_name,
//        |	c.area,
//        |	c.city_name
//        |from user_visit_action a
//        |join product_info p on a.click_product_id = p.product_id
//        |join city_info c on a.city_id = c.city_id
//        |where a.click_product_id > -1
//
//        |""".stripMargin).createOrReplaceTempView("t1")
//    //    session.udf.register("cityRemark",funcions.udaf(new CiryRemarkUDAF()))
//    session.sql(
//      """
//        |select
//        |area,product_name,count(*) clickCnt
//        |cityRemark(city_name) as city_remark
//        |from t1
//        |group by area ,product_name
//        |""".stripMargin).createOrReplaceTempView("t2")
//
//    //区域内点击进行排行
//    session.sql(
//      """
//        |select * ,rank() over(partition by area order by clickCnt desc) as rank from t2
//        |""".stripMargin).createOrReplaceTempView("t3")
//
//    //取top3
//    session.sql(
//      """
//        |select
//        |*
//        |from t3 where rank<=3
//        |""".stripMargin).show(false)
//    session.close()
//  }
//
//  case class Buffer(var total: Long, var cityMap: mutable.Map[String, Long])
//
//  /**
//   * 自定会议聚合函数实现城市备注
//   * in 城市名称
//   * buff [总的点击，map[（city,count）]]
//   * out 备注
//   */
//  class CiryRemarkUDAF extends Aggregator[String, Buffer, String] {
//    //缓冲区的初始化
//    override def zero: Buffer = {
//      //空的缓冲区
//      Buffer(0, mutable.Map[String, Long]())
//    }
//
//    //(city, count)
//    override def reduce(buffer: Buffer, city: String): Buffer = {
//      buffer.total += 1
//      val count: Long = buffer.cityMap.getOrElse(city, 0L) + 1
//      buffer.cityMap.update(city, count)
//      buffer
//    }
//
//    //方法1：合并缓存区
//    override def merge(b1: Buffer, b2: Buffer): Buffer = {
//      b1.total += b2.total
//      val map1: mutable.Map[String, Long] = b1.cityMap
//      val map2: mutable.Map[String, Long] = b2.cityMap
//      //      b1.cityMap = map1.foldLeft(map2) {
//      ////        case (map, (city, cnt)) => {
//      ////          val newcount: Long = map.getOrElse(city, 0L) + cnt
//      ////          map.update(city, newcount)
//      ////          map
//      ////        }
//      ////      }
//      //方法2：2个map的合并操作
//      map2.foreach {
//        case (city, cnt) => {
//          val newc: Long = map1.getOrElse(city, 0L) + cnt
//          map1.update(city, newc)
//        }
//      }
//      b1.cityMap = map1
//      b1
//    }
//
//    override def finish(buff: Buffer): String = {
//      val remarkList: ListBuffer[String] = ListBuffer[String]()
//      val totalCnt: Long = buff.total
//      val cityMap: mutable.Map[String, Long] = buff.cityMap
//      //降序排列
//      val cityList: List[(String, Long)] = cityMap.toList.sortWith(
//        (l, r) => {
//          l._2 > r._2
//        }
//      ).take(2)
//      val hasMore: Boolean = cityMap.size > 2
//      var rsum = 0L
//      cityList.foreach {
//        case (city, cnt) => {
//          val r: Long = cnt * 100 / totalCnt
//          remarkList.append(s"${city} ${r}%")
//          rsum += r
//        }
//      }
//      if (hasMore) {
//        remarkList.append(s"其它 ${100 - rsum}%")
//      }
//      remarkList.mkString(",")
//
//    }
//
//    override def bufferEncoder: Encoder[Buffer] = Encoders.product
//
////    override def outputEncoder: Encoder[String] = Encoders.LONG
//  }
//
//}
