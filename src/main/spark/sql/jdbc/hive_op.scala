package sql.jdbc

import org.apache.log4j.{Level, Logger}
import org.apache.spark
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object hive_op {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session

    val session: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).config("hive.metastore.uris", "thrift://hadoop103:9083").getOrCreate()
    // TODO: sparksql 连接外置的hive
    /**
     * 拷贝hive-site.xml到classpath
     * 启用hive支持
     * 启用mysql驱动
     */
    session.sql("show tables").show
    session.close()
  }
}
