package sql.pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object sparksql2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).config("hive.metastore.uris", "thrift://hadoop103:9083").getOrCreate()
    // TODO: sparksql 连接外置的hive
    session.sql("use myhive")
    session.sql(
      """
        |select * from
        |(
        |select * ,rank() over(partition by area order by clickCnt desc) as rank from
        |(
        |select
        |area,product_name,count(*) clickCnt
        |from
        |(select
        |	a.*,
        |	p.product_name,
        |	c.area,
        |	c.city_name
        |from user_visit_action a
        |join product_info p on a.click_product_id = p.product_id
        |join city_info c on a.city_id = c.city_id
        |where a.click_product_id > -1) t1
        |group by area ,product_name) t2) t3
        |where rank<=3
        |""".stripMargin).show
    session.close()
  }
}
