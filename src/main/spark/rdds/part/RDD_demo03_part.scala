package rdds.part

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_demo03_part {

  def main(args: Array[String]): Unit = {
    // TODO: spark中的分区分配--->1.以行为单位读取  2.数据读取偏移量为单位  3.数据分区偏移量范围
    /**
     * 1@@
     * 2@@
     * 3
     * 7byte 2分区数 [0,3][3,6][6,7]
     * 0=>[0,3] 12
     * 1=>[3,6] 3
     * 2=>[6,7] 空
     */

    //conf对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)
    // TODO: 文件的默认分区   math.min(defaultParallelism, 2)
    //    totalSize += file.getLen();long goalSize = totalSize / (numSplits == 0 ? 1 : numSplits);
    val fileRdd: RDD[String] = sc.textFile("in", 2)
    // 14byte/2 每个分区7byte    总byte 14/7 =2 分区数
    /** 一行为单位读取
     * 1234567@  =>012345678
     * 89@ =>9101112
     * 0 =>13
     * 0# [0-7] 1234567
     * 1# [7-14] 890
     */
    fileRdd.saveAsTextFile("out")

  }
}
