package fuxi.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 分区 默认是cpu核数
 * 分区+数据
 * (0 until numSlices).iterator.map { i =>
 * val start = ((i * length) / numSlices).toInt
 * val end = (((i + 1) * length) / numSlices).toInt
 * (start, end)
 */
object partitions {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)
    //内存创建
//    val mrdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4,5),3)


    //    mrdd.saveAsTextFile("output")

    /**
     * totalSize += file.getLen();
     *
     * long goalSize = totalSize / (numSplits == 0 ? 1 : numSplits);
     * long minSize = Math.max(job.getLong(org.apache.hadoop.mapreduce.lib.input.
     *   FileInputFormat.SPLIT_MINSIZE, 1), minSplitSize);
     */
//    val res: RDD[String] = sc.textFile("in1",2)
    val res: RDD[String] = sc.textFile("in2",2)

    /**
     * 需要读取换行符
     * 1@@ 0-3 => 12
     * 2@@ 3-5 => 3
     * 3   6-7 =>
     */
//    res.saveAsTextFile("out2")
    /**
     * 14byte /2 =7
     * 1234567@@ 0-8  ----> 0-7----
     * 89@@ 9-12  ------> 7-14
     * 0  13
     */
    res.saveAsTextFile("out3")

  }
}
