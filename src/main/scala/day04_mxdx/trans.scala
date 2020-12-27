package day04_mxdx

object trans {
  def main(args: Array[String]): Unit = {
    //byte-short-int-long
    //默认情况下支持多态语法中的类型转换
    // TODO: 隐式转换
    implicit def transf(d: Double): Int = {
      d.toInt
    }

    val i: Int = 5.0
    println(i)
  }
}


