package day04_mxdx

/**
 * 没有接口的概念 只有特质 类似接口
 * 执行顺序 父类--特质 在执行类
 */
object tz {
  def main(args: Array[String]): Unit = {
    //    val u = new U1();
    //    u.name = "lulu"
    //    println(u.name)
    //    u.test()
    new U1()
  }
}

trait t1 {
  println("父类。。。")
  //  var name: String = "lc"
  //
  //  def test(): Unit = {
  //    println("test...")

}

trait t2 {
  println("特质。。。")
}

//可以有多个继承特质 使用with
// TODO: 多特质 混入的时候  代码执行顺序从右到左  方法调用顺序从左到右 如果有父特质会优先执行
// TODO: 动态混入  在不改变源代码的情况下 可以使用with

class U1 extends t1 with t2 {
  println("类。。。...")
}
