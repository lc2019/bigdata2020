package rdds.part

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_demo01 {

  def main(args: Array[String]): Unit = {
    // TODO: rdd的创建
    //conf对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(conf)

    //创建rdd
    // 分区规则 slices.indices.map(i => new ParallelCollectionPartition(id, i, slices(i))).toArray
    // 0 --(0,1)  1
    // 1 (1,3) 23
    // 2 (3,5) 45
    //    (0 until numSlices).iterator.map { i =>
    //      val start = ((i * length) / numSlices).toInt
    //      val end = (((i + 1) * length) / numSlices).toInt
    //      (start, end) //from start until end
    //    }
    val list: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5), 3)
    val list2: RDD[Int] = sc.parallelize(List(1, 2, 3, 4))

    list.collect().foreach(println)
    list2.collect().foreach(println)

    //    list.saveAsTextFile("listoutput")
    //文件读取数据 默认读取的字符串  最小分片规则事hadoop的分配规则
    val fileRdd: RDD[String] = sc.textFile("in")
    fileRdd.saveAsTextFile("output")
    //    fileRdd.collect().foreach(println)
  }
}
