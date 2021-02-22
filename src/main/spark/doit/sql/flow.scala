package doit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 累积上网10分组的数据
 */
object flow {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val df: DataFrame = session.read.option("header", true).csv("datas/data.csv")

    df.createTempView("v_flow")

    session.sql(
      """
        |select
        |	uid,
        |	min(start_time) start_time,
        |	max(end_time) end_time,
        |	sum(flow) total_flow
        |from
        |(
        |	select
        |			uid,
        |			start_time,
        |			end_time,
        |			flow,
        |			sum(flag) over(partition by uid order by start_time asc) sum_flag
        |	from
        |	(
        |		select
        |			uid,
        |			start_time,
        |			end_time,
        |			flow,
        |			if((to_unix_timestamp(start_time) - to_unix_timestamp(lag_time))/60>10 ,1,0) flag
        |		from
        |		(
        |			select
        |				uid,
        |				start_time,
        |				end_time,
        |				flow,
        |				lag(end_time,1) over(partition by uid order by start_time) lag_time
        |			from
        |			v_flow
        |		)t1
        |	)t2
        |)t3
        |group by uid,sum_flag
        |""".stripMargin
    ).show()
  }
}
