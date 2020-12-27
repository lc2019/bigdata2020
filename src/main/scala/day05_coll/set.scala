package day05_coll

object set {
  def main(args: Array[String]): Unit = {
    // TODO: scala默认提供的set是不可变的
    val set1 = Set(1, 2, 3, 4, 1)
    //增加数据
    println(set1 + 11)
    //删除数据
    println(set1 - 11)
  }
}
