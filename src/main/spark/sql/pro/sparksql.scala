package sql.pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object sparksql {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).config("hive.metastore.uris", "thrift://hadoop103:9083").getOrCreate()
    // TODO: sparksql 连接外置的hive
    session.sql("use myhive")
    session.sql(
      """
        |CREATE TABLE `user_visit_action`(
        |  `date` string,
        |  `user_id` bigint,
        |  `session_id` string,
        |  `page_id` bigint,
        |  `action_time` string,
        |  `search_keyword` string,
        |  `click_category_id` bigint,
        |  `click_product_id` bigint,
        |  `order_category_ids` string,
        |  `order_product_ids` string,
        |  `pay_category_ids` string,
        |  `pay_product_ids` string,
        |  `city_id` bigint)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)
    session.sql(
      """
        |load data local inpath 'prodata/user_visit_action.txt' into table myhive.user_visit_action
        |""".stripMargin)

    session.sql(
      """
        |CREATE TABLE `product_info`(
        |  `product_id` bigint,
        |  `product_name` string,
        |  `extend_info` string)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)

    session.sql(
      """
        |load data local inpath 'prodata/product_info.txt' into table myhive.product_info
        |""".stripMargin)

    session.sql(
      """
        |CREATE TABLE `city_info`(
        |  `city_id` bigint,
        |  `city_name` string,
        |  `area` string)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)

    session.sql(
      """
        |load data local inpath 'prodata/city_info.txt' into table myhive.city_info
        |""".stripMargin)

    session.sql("select * from city_info").show
    session.close()
  }
}
