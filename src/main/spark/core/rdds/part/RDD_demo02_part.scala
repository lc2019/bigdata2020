package core.rdds.part

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_demo02_part {

  def main(args: Array[String]): Unit = {
    // TODO: rdd的创建
    //conf对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)
    // TODO: 文件的默认分区   math.min(defaultParallelism, 2)
    //    totalSize += file.getLen();long goalSize = totalSize / (numSplits == 0 ? 1 : numSplits);
    val fileRdd: RDD[String] = sc.textFile("in")
    fileRdd.saveAsTextFile("out")

  }
}
