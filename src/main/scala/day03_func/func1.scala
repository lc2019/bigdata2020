package day03_func

object func1 {
  def main(args: Array[String]): Unit = {
    //    test("函数 调用")
  }

  //函数功能的封装,函数不需要与类型进行绑定
  def test(s: String): Unit = {
    println(s)
  }

  test("hello scala")

  //无参 无返回值
  def test2(): Unit = {
    println("test")
  }

  test2()

  //有参有返回值
  def test3(s: String): String = {
    return s
  }

  val str: String = test3("haha")
  println(str)

  //无参 有返回值
  def test4(): String = {
    return "hello sss"
  }

  println(test4())

  //函数自动推断,省略return 函数体的最后一行代码进行返回
  def test5(): Int = {
    1
  }

  println(test5())

  //如果根据最后一行代码可以推断类型 返回值可以省略
  def test6() = {
    1
  }

  println(test6())

  //如果函数只有1行代码{}可以省略
  def test7() = 2

  println(test7())

  //如果函数没有参数列表 小括号可以省略
  def test8 = "lc"

  println(test8)

  //匿名函数
  () -> println("匿名函数")
}
