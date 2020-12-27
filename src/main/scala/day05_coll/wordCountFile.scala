package day05_coll

// TODO: scala实现wordcount
object wordCountFile {
  def main(args: Array[String]): Unit = {
    val lines = List("hello scala word", "hello word", "hello hadoop", "hello hbase")
    // TODO: 分割
    val words: List[String] = lines.flatMap(line => line.split(" "))
    //将单词放入到元组 变成 w ,1
    val tuples: List[(String, Int)] = words.map(w => (w, 1))

    //元组进行key分组
    val wc: Map[String, List[(String, Int)]] = tuples.groupBy(tp => tp._1)

    //将元组结果进行汇总 reduce
    val stringToInt: Map[String, Int] = wc.map(wc => (wc._1, wc._2.size))

    //将结果进行排序 map不支持排序 转化list
    val res: List[(String, Int)] = stringToInt.toList.sortBy(tp => -tp._2)

    //    val res2: List[(String, Int)] = lines.flatMap(x=>x.split(" ")).groupBy(x=>x).mapValues(x=>x.size).toList.sortBy(x=>x._2).reverse
    println(res)
    //    println(res2)


  }
}
