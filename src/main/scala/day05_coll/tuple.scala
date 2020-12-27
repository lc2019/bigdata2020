package day05_coll

object tuple {
  def main(args: Array[String]): Unit = {
    // TODO: 元组 
    val tuple1 = ("lc", 2020, "jiayou")
    println(tuple1._1)
    println(tuple1._2)
    println(tuple1._3)

    // TODO: 循环
    for (elem <- tuple1.productIterator) {
      println(elem)
    }

    // TODO: 如果元组的元素只有2个 类似map
    val tuple2 = (1, "lc")
    val map1 = Map((1, "lc"))
    //传入元组
    map1.foreach(t => println(t))
    //k,v
    map1.foreach(t => println(t._1 + "--->" + t._2))
  }
}
