package day05_coll

object map {
  def main(args: Array[String]): Unit = {
    val map1 = Map("a" -> 1, "b" -> 2, "c" -> 3)
    println(map1.mkString(","))
    val map2 = map1 + ("d" -> 4)
    println(map2.mkString(","))
    val map3 = map2 - ("b")
    println(map3.mkString(","))
    println(map3.get("d").get)
    //todo scala中提供默认的Option类 有2个特殊方法 Some None 防止出现空指针异常
    println(map3.updated("d", 5))
    // TODO: 没有f给一个值0
    println(map3.get("f").getOrElse(0))

  }
}
