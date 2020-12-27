package day04_mxdx

object gz {
  def main(args: Array[String]): Unit = {
    //    val gzq_ex = new gzq("lc")
    val gzq_ex2 = new gzq()
    //1.wucan--->双参---调用1个参数
    println(gzq_ex2)
  }
}

// TODO: 构造方法：主构造-辅构造
// TODO: 类也是函数 可以使用()作为函数的参数列表,默认也是无参的构造方法 ()可以省略
// TODO: 在类后面声明的就是主构造方法，在主构造方法声明的就是辅助构造方法，辅助必须调用主构造方法
class gzq(s: String) {
  println("主构造方法")
  println(s)

  def this(s: String, ss: String) {
    this(s)
    println("辅助构造方法2")
  }

  def this() {
    this("辅助构造方法", "调用其他构造方法必须西先声明")
  }

}
