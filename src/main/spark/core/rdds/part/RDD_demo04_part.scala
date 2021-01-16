package core.rdds.part

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}
// TODO: 自定义分区器

object demo1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)
    sc.setCheckpointDir("ck")
    val rdd = sc.makeRDD(List(
      ("2020", "hello scala"),
      ("2021", "hello spark"),
      ("2019", "bigdata"),
      ("2021", "hello bigdata")
    ), 3)
    //def partitionBy(partitioner: Partitioner): RDD[(K, V)] = self.withScope {
    val prdd: RDD[(String, String)] = rdd.partitionBy(new MyPart)
    prdd.saveAsTextFile("out")
    sc.stop()


  }
}

// TODO: 自定义分区 继承 Partitioner
class MyPart extends Partitioner {
  //分区数量
  override def numPartitions: Int = 3

  //分区规则 返回int 根据数据的key返回数据的分区索引从0开始
  override def getPartition(key: Any): Int = {
    key match {
      case "2019" => 0
      case "2020" => 1
      case _ => 2
    }
  }
}