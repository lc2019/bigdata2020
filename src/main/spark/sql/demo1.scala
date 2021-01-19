package sql


import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object demo1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    // TODO: 创建session
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //隐式转换
    import session.implicits._

    //    // TODO: 逻辑
    //    //DataFrame
    //    val df: DataFrame = session.read.json("datas/user.json")
    //    //    df.show()
    //    //df -sql
    //    df.createOrReplaceTempView("user")
    //    session.sql("select * from user").show
    //    session.sql("select username from user").show
    //
    //    //df-dsl
    //    df.select("age", "username").show
    //
    //    df.select('age + 1).show
    //    //DataSet
    //    val ints: Seq[Int] = Seq(1, 2, 3, 4)
    //    val ds: Dataset[Int] = ints.toDS()
    //    ds.show

    //rdd-dataframe
    val rdd: RDD[(Int, String, Int)] = session.sparkContext.makeRDD(List((1, "zhangsan", 30), (2, "lisi", 28), (3, "wangwu", 20)))
    val df: DataFrame = rdd.toDF("id", "name", "age")
    val rowrdd: RDD[Row] = df.rdd

    //df-ds 样例类
    val ds: Dataset[User] = df.as[User]
    val dataFrame: DataFrame = ds.toDF()

    //rdd-ds
    val ds1: Dataset[User] = rdd.map {
      case (id, name, age) => {
        User(id, name, age)
      }
    }.toDS()
    ds1.rdd

    // TODO: 关闭
    session.close()
  }

  case class User(Id: Int, name: String, age: Int)

}
