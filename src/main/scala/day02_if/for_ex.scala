package day02_if

import scala.collection.immutable.Range
import scala.util.control.Breaks

object for_ex {
  def main(args: Array[String]): Unit = {
    //循环范围1-5
    for (elem <- 1 to 5) {
      println(elem)
    }
    //不包含5
    for (elem <- 1 until (5)) {
      println(elem)
    }
    //0-5 步长2
    for (i <- Range(0, 5, 2)) {
      println(i)
    }

    /**
     * *
     * ***
     * *****
     */
    //杨辉三角
    for (i <- Range(1, 18, 2)) {
      println(" " * ((18 - i) / 2) + "*" * i + " " * ((18 - i) / 2))
    }

    /**
     * 循环守卫,类似continue
     */
    for (i <- Range(0, 5) if i != 2) {
      println(i)
    }

    for {i <- Range(1, 18, 2)
         j = (18 - i) / 2} {
      println(" " * j) + "*" * i + (" " * j)
    }

    //默认情况下 for循环的返回值是（），采用yield后可将结果保存在集合中
    val ints = for (i <- 1 to 10) yield i * 2
    println(ints)
    //    Vector(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
    //try catch
    Breaks.breakable {
      for (elem <- 1 to 5) {
        if (elem == 4) {
          //直接抛出异常来终止整个循环
          Breaks.break()
        }
        println(elem)
      }
      println("循环结束")
    }

  }
}
