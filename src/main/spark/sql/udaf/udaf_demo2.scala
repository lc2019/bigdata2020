package sql.udaf

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, SparkSession, functions}

object udaf_demo2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //隐式转换

    val df: DataFrame = session.read.json("datas/user.json")

    df.createOrReplaceTempView("user")
    //自定义udf 强类型udaf spark3.0才支持 2.0版本不能在sql使用强类型udaf操作
    // TODO: dsl使用强类型的udaf


    //    session.udf.register("avgAge", functions.udaf(new avgUdaf()))
    session.sql("select avgAge(age) from user").show
    // TODO: 关闭
    session.close()
  }

  /**
   * 自定义聚合函数  计算平均值
   * IN输入数据类型
   * OUT输出数据类型
   * BUF 缓冲区
   */
  case class Buff(var total: Long, var count: Long)

  case class User(username: String, age: Long)

  class avgUdaf extends Aggregator[Long, Buff, Long] {
    //缓冲区初始化
    override def zero: Buff = {
      //total count
      Buff(0L, 0L)
    }

    //根据输入数据更新缓存区数据
    override def reduce(buff: Buff, in: Long): Buff = {
      buff.total = buff.total + in
      buff.count = buff.count + 1
      buff
    }

    //合并缓冲区
    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.total = b1.total + b2.total
      b1.count = b1.count + b2.count
      b1
    }

    //计算逻辑
    override def finish(buff: Buff): Long = {
      buff.total / buff.count
    }

    override def bufferEncoder: Encoder[Buff] = Encoders.product

    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }

}


