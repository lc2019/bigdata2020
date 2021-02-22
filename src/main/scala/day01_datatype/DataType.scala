package day01_datatype

object DataType {
  def main(args: Array[String]): Unit = {
    println(Unit)
    val s = "123"
    println(s.toInt)
    println(s.getClass.getSimpleName)
    //scala中_有特殊用途，不能当作变量来使用

    val i = 123
    println(i.toString)
    //查看变量类型
    println(i.getClass)


  }
}
