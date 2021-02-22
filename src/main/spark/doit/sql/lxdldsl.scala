//package doit.sql
//
//import org.apache.log4j.{Level, Logger}
//import org.apache.spark.SparkConf
//import org.apache.spark.sql.expressions.Window
//import org.apache.spark.sql.{DataFrame, SparkSession}
//
///**
// * 解题思路
// * 根据第一次登陆的时间，按照日期进行排名，按照日期大小排序后减去排名就可以算出最初的天数
// * 然后在uid,date_diff分组 计算出现的次数 大于等于3次就是连续登陆的结果
// */
//object lxdldsl {
//  def main(args: Array[String]): Unit = {
//    Logger.getLogger("org").setLevel(Level.ERROR)
//    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
//    // TODO: 创建session
//    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
//
//    val df1: DataFrame = session.read.option("header", true).csv("datas/data1.txt")
//
//    //隐式转换
//    import session.implicits._
//    //sql方法
//    import org.apache.spark.sql.functions._
//    //得到去重后的数据
//    val res= df1.distinct().select(
//      $"uid",
//      $"dt",
//      //别名
//      row_number() over(Window.partitionBy("uid").orderBy("dt")) as "rn"
////      row_number().over(Window.partitionBy("uid").orderBy("dt")).as("rn")
//    ).select(
//      $"uid",
//      'dt,
//      date_sub('dt,'rn) as "date_dif"
//    ).groupBy(
//      "uid","date_dif"
//    ).agg(
//      count("*") as "times"
//      min("dt") as "start_date"
//      max("dt") as "end_date"
//    ).where("times">=3)
//
//    //划分窗口
//    res.show()
//  }
//}
