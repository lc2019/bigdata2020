package doit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 解题思路
 * 根据第一次登陆的时间，按照日期进行排名，按照日期大小排序后减去排名就可以算出最初的天数
 * 然后在uid,date_diff分组 计算出现的次数 大于等于3次就是连续登陆的结果
 */
object lxdl {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //隐式转换
    val df1: DataFrame = session.read.option("header", true).csv("datas/data1.txt")
    df1.createTempView("v_user_login")
    session.sql(
      """
        |select
        |	uid,
        |	min(dt) start_date,
        |	max(dt) end_date,
        |	count(*) times
        |from
        |(
        |	select
        |	 	uid,
        |	 	dt,
        |	 	date_sub(dt,rn) date_dif
        |	 from
        |	 (
        |
        |		select
        |			uid,
        |			dt,
        |			row_number() over(partition by uid order by dt asc) rn
        |		from
        |		(
        |			select
        |		     	dis.uid uid,
        |		     	dis.dt dt
        |		 from
        |		 	(select
        |				 distinct(uid,dt) dis
        |		   	from v_user_login ) t1
        |		)t2
        |	) t3
        |) t4
        |group by uid,date_dif having times>=3
        |""".stripMargin).show()
  }
}
