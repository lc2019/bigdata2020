package doit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}


object xiaoshoue {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val df: DataFrame = session.read.option("header", true).csv("datas/shop.csv")

    df.createTempView("v_shop")

    session.sql(
      """
        |select
        |		sid,
        |		mth,
        |		mth_income,
        |		sum(mth_income) over(partition by sid order by mth ) total_money
        |	from
        |	(
        |			select
        |		sid,
        |		mth,
        |		sum(money) mth_income
        |	from
        |	(
        |				select
        |					sid,
        |					substr(dt,0,7) mth,
        |					cast(money as double) money
        |				from v_shop
        |				)t1
        |			group by sid,mth
        |		)t2
        |""".stripMargin
    ).show()
  }
}
