package sql.udaf

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object demo2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //隐式转换

    val df: DataFrame = session.read.json("datas/user.json")

    df.createOrReplaceTempView("user")
    //自定义udf
    session.udf.register("avgAge", new myUdaf())
    session.sql("select avgAge(age) from user").show
    // TODO: 关闭
    session.close()
  }

  /**
   * 自定义聚合函数  继承UserDefinedAggregateFunction
   */
  class myUdaf extends UserDefinedAggregateFunction {
    //输入数据结构
    override def inputSchema: StructType = {
      StructType(
        Array(
          StructField("age", LongType)
        )
      )
    }

    //缓冲区结构
    override def bufferSchema: StructType = {
      StructType(
        Array(
          StructField("total", LongType),
          StructField("count", LongType)
        )
      )
    }

    //函数计算结果的数据类型
    override def dataType: DataType = LongType

    override def deterministic: Boolean = true

    //缓冲区初始化
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      //      buffer(0) = 0L
      //      buffer(1) = 0L
      buffer.update(0, 0L)
      buffer.update(1, 0L)
    }

    //根据输入的值跟新缓冲区
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      //total
      buffer.update(0, buffer.getLong(0) + input.getLong(0))
      //count
      buffer.update(1, buffer.getLong(1) + 1)
    }

    //缓冲区数据合并
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1.update(0, buffer1.getLong(0) + buffer2.getLong(0))
      buffer1.update(1, buffer1.getLong(1) + buffer2.getLong(1))

    }

    //计算
    override def evaluate(buffer: Row): Any = {
      buffer.getLong(0) / buffer.getLong(1)
    }
  }

}
