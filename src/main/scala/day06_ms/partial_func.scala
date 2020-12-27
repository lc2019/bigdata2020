package day06_ms

// TODO: 偏函数
object partial_func {
  def main(args: Array[String]): Unit = {
    val list: List[Any] = List(1, 2, 3, 4, "abc")
    //让函数的数字都+1 字母不变
    val list1: List[AnyVal] = list.map(x => {
      if (x.isInstanceOf[Int]) {
        x.asInstanceOf[Int] + 1
      }
    })
    println(list1)
    //过滤
    val list2: List[AnyVal] = list1.filter(x => x.isInstanceOf[Int])
    println(list2)

    // TODO: 偏函数实现
    val add: PartialFunction[Any, Int] = new PartialFunction[Any, Int] {
      override def isDefinedAt(x: Any) = {
        if (x.isInstanceOf[Int]) true else false
      }

      override def apply(x: Any) = {
        x.asInstanceOf[Int] + 1
      }
    }
    println(list.collect(add))
    // TODO: phs实现
    val ints: List[Int] = list.collect {
      case i: Int => i + 1
    }
    println(ints)

    val strings: List[String] = list.map {
      case num => num + "xx"
    }
    println(strings)

    val list3: List[Any] = list.sortWith {
      case (l, r) => {
        l.toString > r.toString
      }
    }
    println(list3)
  }
}
