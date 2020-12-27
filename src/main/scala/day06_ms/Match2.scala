package day06_ms

object Match2 {
  def main(args: Array[String]): Unit = {
    val (x, y) = (1, 2)
    println(x + " " + y)

    // TODO: 模式匹配的方式来进行取值
    val list = List(("a", 1), ("b", 2), ("c"), 3)
    for ((k, v) <- list) {
      println(k + " " + v)
    }
  }
}
