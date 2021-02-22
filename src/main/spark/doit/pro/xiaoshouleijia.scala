package doit.pro

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 累加销售额
 * shop1,2019-01-18,500
 * shop1,2019-02-10,500
 * shop1,2019-02-10,200
 * shop1,2019-02-11,600
 * shop1,2019-02-12,400
 * shop1,2019-02-13,200
 * shop1,2019-02-15,100

 *计算店铺的销售额和累加到当前月的销售和
 */
object xiaoshouleijia {
  def main(args: Array[String]): Unit = {
    //conf对象
    val wcConf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
    //创建上下文对象
    val sc = new SparkContext(wcConf)

    val lines: RDD[String] = sc.textFile("datas/shop.txt")

    //根据sid 月份进行聚合 shop1,2019-01-18,500
    val rdd: RDD[((String, String), Double)] = lines.map(
      item => {
        val fields: Array[String] = item.split(",")
        val sid: String = fields(0)
        val datestr: String = fields(1)
        val mth: String = datestr.substring(0, 7)
        val money: Double = fields(2).toDouble
        ((sid, mth), money)
      }
    ).reduceByKey(_ + _)

    //分组排序
    val res: RDD[(String, String, Double, Double)] = rdd.groupBy(_._1._1).mapValues(
      iter => {
        //月份分组
        val list: List[((String, String), Double)] = iter.toList.sortBy(_._1._2)
        var rollup = 0.0
        list.map(
          item => {
            val sid: String = item._1._1
            val mth: String = item._1._2
            val mth_sales = item._2
            rollup += mth_sales
            (mth, mth_sales, rollup)
          }
        )
      }
    ).flatMapValues(lst => lst).map {
      //(sid, (mth,mth_sales,rollup))
      t => (t._1, t._2._1, t._2._2, t._2._3)
    }
    res.collect().foreach(println)
  }
}
