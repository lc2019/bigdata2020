package sql.udf

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object demo2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //隐式转换
    import session.implicits._

    val df: DataFrame = session.read.json("datas/user.json")

    df.createOrReplaceTempView("user")
    //自定义udf
    session.udf.register("prefixName", (name: String) => {
      "name: " + name
    })
    session.sql("select age ,prefixName(username) from user").show
    // TODO: 关闭
    session.close()
  }

  case class User(Id: Int, name: String, age: Int)

}
