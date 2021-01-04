package pro

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

// TODO: 优化 累加器 避免shuffle的过程
object demo4 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    //1.读取原始日志
    val ardd: RDD[String] = sc.textFile("datas")
    val acc = new diyAcc
    sc.register(acc, "wc")
    // TODO: 重复使用的rdd使用缓存 
    //2.直接将数据转换结构
    /**
     * 点击：品类id,(1,0,0)
     * 下单：品类id,(0,1,0)
     * 支付：品类id,(0,0,1)
     */
    ardd.foreach(
      action => {
        val datas: mutable.ArrayOps[String] = action.split("_")
        if (datas(6) != "-1") {
          acc.add(datas(6), "click")
        } else if (datas(8) != "null") {
          var ids: Array[String] = datas(8).split(",")
          ids.foreach(
            id => {
              acc.add((id, "order"))
            }
          )
        } else if (datas(10) != "null") {
          var ids: Array[String] = datas(10).split(",")
          ids.foreach(
            id => {
              acc.add((id, "pay"))
            }
          )
        } else {
          Nil
        }
      }
    )
    val value: mutable.Map[String, Category] = acc.value
    val categories: mutable.Iterable[Category] = value.map(_._2)
    val res: List[Category] = categories.toList.sortWith(
      (l, r) => {
        if (l.clickCnt > r.clickCnt) {
          true
        } else if (l.clickCnt == r.clickCnt) {
          l.payCnt > r.payCnt
        } else {
          false
        }
      }

    )

    //打印
    res.take(10).foreach(println)
  }

  case class Category(cid: String, var clickCnt: Int, var orderCnt: Int, var payCnt: Int)

  /**
   * 累加器避shuffle
   * IN（品类。行为）点击下单支付
   * OUT mutable.Map[String,Category]
   */
  class diyAcc extends AccumulatorV2[(String, String), mutable.Map[String, Category]] {
    private val hcMap: mutable.Map[String, Category] = mutable.Map[String, Category]()

    override def isZero: Boolean = {
      hcMap.isEmpty
    }

    override def copy(): AccumulatorV2[(String, String), mutable.Map[String, Category]] = {
      new diyAcc
    }

    override def reset(): Unit = {
      hcMap.clear()
    }

    override def add(v: (String, String)): Unit = {
      val cid: String = v._1
      val action: String = v._2
      val category: Category = hcMap.getOrElse(cid, Category(cid, 0, 0, 0))
      if (action == "click") {
        category.clickCnt += 1
      } else if (action == "order") {
        category.orderCnt += 1
      } else if (action == "pay") {
        category.payCnt += 1
      }
      hcMap.update(cid, category)
    }

    override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, Category]]): Unit = {
      val map1: mutable.Map[String, Category] = this.hcMap
      val map2: mutable.Map[String, Category] = other.value
      map2.foreach {
        case (cid, hc) => {
          val category: Category = map1.getOrElse(cid, Category(cid, 0, 0, 0))
          category.clickCnt += hc.clickCnt
          category.orderCnt += hc.orderCnt
          category.payCnt += hc.payCnt
          map1.update(cid, category)
        }
      }
    }

    override def value: mutable.Map[String, Category] = {
      hcMap
    }
  }

}
