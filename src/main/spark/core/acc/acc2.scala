package core.acc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable


object acc2 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // TODO: local 模式--conf对象--计算框架--appid
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)

    //读取文件,一行一行的读取 从文件创建rdd
    val rdd: RDD[String] = sc.makeRDD(List("hello", "scala"))
    val wacc = new MyAcc()
    //注册
    sc.register(wacc, "wc")
    rdd.foreach(
      words => {
        wacc.add(words)
      }
    )
    println(wacc.value)
  }
}

/**
 * 自定义累加器 不用shuffle
 * 继承AccumulatorV2[IN,OUT] 定义泛型
 * IN 累加器的输入数据类型 String
 * OUT 累加器的输出数据类型 mutable.Map[String, Long]
 */
class MyAcc extends AccumulatorV2[String, mutable.Map[String, Long]] {
  private var wcMap: mutable.Map[String, Long] = mutable.Map[String, Long]()

  //判断是否是初始状态
  override def isZero: Boolean = {
    wcMap.isEmpty
  }

  override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = {
    new MyAcc()
  }

  override def reset(): Unit = {
    wcMap.clear()
  }

  //获取累加器需要累加的值
  override def add(word: String): Unit = {
    //直接查询map里面的值 有+1 没有返回0
    val count: Long = wcMap.getOrElse(word, 0L) + 1
    //更新到map
    wcMap.update(word, count)
  }

  //driver合并多个累加器
  override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
    val map1 = this.wcMap
    val map2 = other.value
    map2.foreach {
      case (word, count) => {
        val res: Long = map1.getOrElse(word, 0L) + count
        map1.update(word, res)
      }
    }
  }

  override def value: mutable.Map[String, Long] = {
    wcMap
  }
}