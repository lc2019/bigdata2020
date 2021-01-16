package sql.jdbc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object jdbc_op {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //隐式转换
    import session.implicits._

    session.read
      .format("jdbc")
      .option("url", "jdbc:mysql://192.168.124.203:3306/company")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", "staff")
      .load().show

    session.close()
  }
}
