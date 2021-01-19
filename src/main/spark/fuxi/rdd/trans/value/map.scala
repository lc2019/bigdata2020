package fuxi.rdd.trans.value

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object map {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //创建上下文对象
    val sc = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    //    rdd.map((num:Int)=>{num*2})

    /**
     * 当函数代码只有一行{}可以省略
     * 参数类型可以自动推断，类型可以省略
     * 当参数只有一个（）可以省略
     * 当参数按照顺序出现可以用_代替
     */
    //    rdd.map(_*2)
//    val mrdd: RDD[Int] = rdd.mapPartitions(iter => {
//      println("****")
//      iter.map(_ * 2)
//    })
    //以分区为单位进行转换操作，可以将整个分区的数据加载到内存，处理完的数据不会被释放，存在对象的引用
    //如果数据量大可能造成内存溢出

    //分区去最大 必须返回迭代器
//    val mrdd: RDD[Int] = rdd.mapPartitions(
//      iter => {
//        List(iter.max).iterator
//      }
//    )
//    val mrdd: RDD[Int] = rdd.mapPartitionsWithIndex(
//      // TODO: 分区号 
//      (index, iter) => {
//        if (index == 1) {
//          iter
//        } else {
//          Nil.iterator
//        }
//      }
//    )

    val mrdd: RDD[(Int, Int)] = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        // TODO: 带上分区号的数据
        iter.map {
          num => (index, num)
        }
      }
    )
    mrdd.collect().foreach(println)
  }
}
