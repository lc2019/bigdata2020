package core.rdds.action_rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object serial {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
    val sh = new search("h")
    sh.getMatch1(rdd).collect().foreach(println)


  }

  // TODO: 类的构造参数其实是类的属性 ，构造参数需要进行闭包检测 等同类进行闭包检测
  class search(query: String) extends Serializable {
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    //函数序列化
    def getMatch1(rdd: RDD[String]): RDD[String] = {
      rdd.filter(isMatch)
    }

    //熟悉序列化
    def getMatch2(rdd: RDD[String]): RDD[String] = {
      rdd.filter(x => x.contains(query))
    }
  }

}
