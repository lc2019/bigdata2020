package day02_if

object exp {
  def main(args: Array[String]): Unit = {
    val flag = true
    //返回值是满足条件代码体的最后一行内容
    val value = if (flag) {
      "abc"
      println("哈哈") //println没有返回值 unit
    } else {
      "bcd"
    }
    println(value) //---这里是any 类型
    //三元运算符
    val res = if (flag) 1 else 0
    println(res)

    val unit: Any = if (flag) {
      "abc"
    }
    unit

    val a = 40
    val b = 20
    val c = 30
    var max = 0
    if (a >= b) {
      max = if (a >= c) a else c

    } else {
      max = if (b >= c) b else c
    }
    println(max)
  }
}
