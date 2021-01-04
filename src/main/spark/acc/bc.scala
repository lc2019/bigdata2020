package acc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object bc {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val srdd = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
    val map: mutable.Map[String, Int] = mutable.Map(("a", 4), ("b", 5), ("c", 6))
    val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)
    // TODO: (a,(1,4))
    srdd.map {
      case (w, c) => {
        //map取值
        val count: Int = bc.value.getOrElse(w, 0)
        (w, (c, count))
      }
    }.collect().foreach(println)
  }
}