package rdds.exchange_rdd.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object diyPartition {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    val lrdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))

    val myrdd: RDD[(String, Int)] = lrdd.partitionBy(new mypart(3))
    myrdd.saveAsTextFile("out3")


  }
}

class mypart(ps: Int) extends Partitioner {
  override def numPartitions: Int = {
    ps
  }

  // TODO: 放到1号分区 
  override def getPartition(key: Any): Int = {
    1
  }
}
