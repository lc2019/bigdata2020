package doit.pro

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * topN的案例
 * 1.数据切分，转换为想要的数据结构        ((subject, TeacName),1)
 * 2.聚合  ((subject, TeacName),sum)
 * 3.学科分组subject
 * 4.tolist排序，结构转换 ((subject, TeacName),sum) 排序 （TeacName,sum）
 * 5.输出
 */
object topN {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val lines: RDD[String] = sc.textFile("datas/teacher.log")
    //http://bigdata.51doit.cn/laozhang
    val subJectName: RDD[((String, String),Int)] = lines.map(
      e => {
        val strings: Array[String] = e.split("/")
        //url
        val url: String = strings(2)
        //url按.切割 课程
        val subject: String = url.split("[.]")(0)
        //转换为（（课程，老师），1）
        val TeacName: String = strings(3)
        ((subject, TeacName),1)
      }
    )
    //聚合((subject, TeacName),sum)
    val rdd1: RDD[((String, String), Int)] = subJectName.reduceByKey(_ + _)

    //全局排序，需要先进行学科分组((subject, TeacName),sum)
    val grdd: RDD[(String, Iterable[((String, String), Int)])] = rdd1.groupBy(_._1._1)
    //分组后((subject, TeacName),sum)--->排序((TeacName,sum)）
    val res: RDD[(String, (String, Int))] = grdd.flatMapValues(it => {
      val tuples: List[((String, String), Int)] = it.toList.sortBy(-_._2)
      tuples.map(t => (t._1._2, t._2)).take(2)
    })

    println(res.collect().toBuffer)
    sc.stop()
  }
}
