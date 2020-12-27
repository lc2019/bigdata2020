package day05_coll

// TODO: scala实现wordcount 
object wordCount {
  def main(args: Array[String]): Unit = {
    val lists: List[(String, Int)] = List(("hello scala word", 4), ("hello word", 3), ("hello hadoop", 3), ("hello hbase", 1))
    // TODO: 转换成一行一行的数据 hello 4 scala 4
    val fmlist: List[(String, Int)] = lists.flatMap(t => {
      //      //"hello scala word"
      //      val ss: String = t._1
      //      //切分 " "
      //      val words = ss.split(" ")
      //      //重组map => hello 4
      //      words.map(w => (w, t._2))
      t._1.split(" ").map(w => (w, t._2))
    })

    // TODO:单词进行分组 list(hello,4) 获取key
    val stringToList = fmlist.groupBy(t => t._1)

    // TODO:分组结构在进行转换
    //    val wc = stringToList.map(t => {
    //      //只保留数字
    //      val list = t._2.map(tt => tt._2)
    //      (t._1, list.sum)
    //    })

    //hello 4 ----list(4)----list.sum 操作v
    val wc: List[(String, Int)] = stringToList.mapValues(datas => datas.map(t => t._2).sum).toList.sortWith((l, r) => {
      l._2 > r._2
    })
    println(wc.take(3))

  }
}
