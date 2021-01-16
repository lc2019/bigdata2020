package core.acc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator

object acc {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    //core.acc 提供简单数据聚合的累加器
    val sumacc: LongAccumulator = sc.longAccumulator("sum")
    var sum = 0
    // TODO: executor的sum结果一直没有返回 所以打印0
    srdd.foreach(
      num => {
        //        sum += num
        // TODO: 使用累加器
        sumacc.add(num)
      }
    )
    println(sumacc.value)
  }
}