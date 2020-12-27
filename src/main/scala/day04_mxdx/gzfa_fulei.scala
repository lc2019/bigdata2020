package day04_mxdx

//父类构造方法
object gzfa_fulei {
  def main(args: Array[String]): Unit = {
    val user = new zileigz();

  }
}

class fuleigz(str: String) {
  println("zhu gz " + str)
}

// TODO: 如果父类有构造必须传递参数 父类()等同传递参数 必须显示传递参数
class zileigz(s: String) extends fuleigz(s) {
  println("zi gouzao")

  // TODO: 辅助构造
  def this() {
    this("xxx") //super(s)
    println("fuzhu gz")
  }
}
