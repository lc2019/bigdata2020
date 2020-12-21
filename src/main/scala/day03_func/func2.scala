package day03_func

object func2 {
  def main(args: Array[String]): Unit = {

    //todo 可变参数 返回的一个集合WrappedArray(lc, lulu)
    def test(name: String*): Unit = {
      println(name)
    }

    test("lc", "lulu")

    //todo 默认参数 声明的时候直接赋值
    def test2(name: String, age: Int = 20): Unit = {
      println(name + "-" + age)
    }

    test2("lc", 30)
    //todo 没有就是默认值
    test2("lc")

    def test3(name: String = "lc", name2: String): Unit = {
      println(name + "-" + name2)
    }
    //传入参数
    test3("ll", "hk")
    //指定传入的参数
    test3(name2 = "lc")
  }
}
