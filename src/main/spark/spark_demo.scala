import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object spark_demo {
  def main(args: Array[String]): Unit = {
    // TODO: local 模式--conf对象--计算框架--appid
    //conf对象
    val wcConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")

    //创建上下文对象
    val sc = new SparkContext(wcConf)

    //读取文件,一行一行的读取
    val lines: RDD[String] = sc.textFile("input")

    //分解
    val tp: RDD[String] = lines.flatMap(_.split(" "))

    //结构转换 key ,1
    val key: RDD[(String, Int)] = tp.map((_, 1))

    //分组聚合
    val res: RDD[(String, Int)] = key.reduceByKey(_ + _)

    //采集输出结果
    val rest: Array[(String, Int)] = res.collect()
    rest.foreach(println)

  }
}
