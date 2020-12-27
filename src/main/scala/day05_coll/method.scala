package day05_coll

// TODO: 方法 
object method {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 4, 3, 4, 5, 3, 2)
    // TODO: 分组 
    val intToInts = list.groupBy(i => i % 2)
    intToInts.foreach(t => println(t._1 + "--->" + t._2))

    // TODO: 通过指定函数的返回值进行分组
    val map1: Map[Int, List[Int]] = list.groupBy(x => x)
    map1.foreach(x => println(x._1 + "--->" + x._2))

    val strings: List[String] = List("13", "12", "21", "24")
    // TODO: 第一个字母进行分组
    val strs: Map[String, List[String]] = strings.groupBy(s => s.substring(0, 1))
    strs.foreach(s => println(s._1 + "----" + s._2))

    //sort by
    val list1 = list.sortBy(x => x)
    println(list1.mkString(","))
    // TODO: scala 支持自己定义排序规则
    val ss: List[String] = strings.sortBy(s => s.substring(1))
    println(ss.mkString(","))

    // TODO: sort with
    //    val ints: List[Int] = list.sortWith((x, y)=>x>y)
    //    ints.foreach(x=>println(x))

    val ints: List[Int] = list.sortWith((x, y) => x < y)
    ints.foreach(x => println(x))
    // TODO: 自定义比较规则
    val ss1: List[String] = strings.sortWith((l, r) => {
      l.substring(1).toInt > r.substring(1).toInt
    })
    println(ss1.mkString(","))

    // TODO: 映射转换
    //x--x,x
    val tuples: List[(Int, Int)] = list.map(x => (x, 1))
    //使用第一个元素排序
    val intToTuples: Map[Int, List[(Int, Int)]] = tuples.groupBy(t => t._1)
    //k-list ---k.size
    val intToInt: Map[Int, Int] = intToTuples.map(t => (t._1, t._2.size))
    println(intToInt.mkString(","))

    // TODO: wordcount
    val ws: List[String] = List("hello", "hello", "hive", "hbase", "scala", "scala", "kafka", "kafka")
    //1.分组 相同的单词放置在一起 (hello,list(hello,hello))
    val s2s: Map[String, List[String]] = ws.groupBy(word => word)
    //    println(s2s.mkString(","))
    //2.将数据结构进行转换 ->hello,hello.size
    val stringToInt: Map[String, Int] = s2s.map(t => (t._1, t._2.size))
    //进行排序 list((k1,v1),(k2,v2)),map是无序的
    val tuples1: List[(String, Int)] = stringToInt.toList.sortWith((l, r) => {
      l._2 > r._2
    })
    //获取排序的前3
    println(tuples1.take(3).mkString(","))

    // TODO: 扁平化操作 处理行的数据
    val ws2: List[String] = List("hello hello", "hive hbase", "hello scala scala", "hello kafka kafka")
    val res: List[(String, Int)] = ws2.flatMap(x => x.split(" ")).groupBy(word => word).map(t => (t._1, t._2.size)).toList.sortWith((l, r) => {
      l._2 > r._2
    })
    println(res.mkString(","))
  }
}
