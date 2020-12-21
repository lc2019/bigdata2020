package day02_if

object lckz {
  def main(args: Array[String]): Unit = {
    val flag = true
    if (!flag) {
      println("true")
    } else {
      println("false")
    }

    val x = 4
    val y = 1
    if (x > 2) {
      if (y > 2) print("haha")
      println("hello scala")
    } else {
      println("没通过第一关")
    }
  }
}
