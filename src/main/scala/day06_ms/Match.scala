package day06_ms

object Match {
  def main(args: Array[String]): Unit = {
    val oper = '*'
    val n1 = 20
    val n2 = 10
    val n3 = 0
    var res = 0
    oper match {
      case '+' => res = n1 + n2
      case '-' => res = n1 - n2
      case '*' => res = n1 * n2
      case '/' => res = n1 / n2
      case _ => println("over") //类似java的*
    }
    println(res)

    // TODO:  _ 下划线在模式匹配中相当于* 在能推导的场景中相当于参数 
    val list: List[List[Int]] = List(List(1, 2), List(3, 4))
    val ints: List[Int] = list.flatMap(x => x)
    //    val ints2: List[Int] = list.flatMap(_) //不能自动推导是参数还是函数
    println(ints)

    val strings: List[String] = List("hello world", "hello scala")
    val list1: List[String] = strings.flatMap(_.split(" "))
    println(list1.mkString(","))
  }
}
