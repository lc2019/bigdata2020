package day05_coll

object method2 {
  def main(args: Array[String]): Unit = {
    val list: List[Int] = List(1, 2, 3, 4)
    // TODO: 过滤 
    val list2: List[Int] = list.filter(x => x % 2 == 0)
    println(list2.mkString(","))

    // TODO: 集合的方法
    val list3: List[Int] = List(3, 4, 5, 6, 7)
    //拉链
    println(list3.zip(list).mkString(","))
    //合集
    println(list3.union(list).mkString(","))
    //差集
    println(list3.diff(list).mkString(","))
    //交集
    println(list3.intersect(list).mkString(","))
  }
}
