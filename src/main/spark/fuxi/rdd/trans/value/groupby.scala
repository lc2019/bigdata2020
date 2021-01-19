package fuxi.rdd.trans.value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object groupby {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd: RDD[String] = sc.makeRDD(List("hello", " scala", " spark"))
    // TODO: 分组依据 
    val mrdd: RDD[(Char, Iterable[String])] = srdd.groupBy(_.charAt(0))
    mrdd.collect().foreach(println)
  }
}
