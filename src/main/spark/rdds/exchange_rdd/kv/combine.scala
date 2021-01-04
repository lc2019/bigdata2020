package rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * /**
 * * :: Experimental ::
 * * Simplified version of combineByKeyWithClassTag that hash-partitions the resulting RDD using the
 * * existing partitioner/parallelism level.
 **/
 * def combineByKeyWithClassTag[C](
 * createCombiner: V => C,  相同key
 * mergeValue: (C, V) => C,  分区内
 * mergeCombiners: (C, C) => C) 分区间
 * }
 */
object combine {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")


    //创建上下文对象
    val sc = new SparkContext(wcConf)
    // TODO: 2个分区 
    val initrdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)), 2)

    val res: RDD[(String, (Int, Int))] = initrdd.combineByKey(
      //结构转换 （"a",(88,1)）（"a",99）
      (_, 1),
      //分区内---->   ((88,1),99) （（v1,次数），v2）=>对v进行相加 =>（187，2）
      (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
      //分区间 元组（187，2）
      (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    )
    //    结果(b,(286,3)) (a,(274,3))
    res.collect().foreach(println)
    //计算平均值
    res.map {
      case (k, v) => {
        (k, v._1 / v._2.toDouble)
      }
    }.collect().foreach(println)
  }

}
