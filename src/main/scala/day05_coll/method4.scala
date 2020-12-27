package day05_coll

object method4 {
  def main(args: Array[String]): Unit = {
    val list = List(1, List(2, 3), List(4, 5), 6, List(7, 8))
    val list1: List[Any] = list.flatMap(any => {
      //判断是否是list
      if (any.isInstanceOf[List[Int]]) {
        any.asInstanceOf[List[Int]]
      } else {
        List(any)
      }
    })
    println(list1)

  }
}
