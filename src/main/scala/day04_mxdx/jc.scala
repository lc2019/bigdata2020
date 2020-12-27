package day04_mxdx

object jc {
  def main(args: Array[String]): Unit = {
    val user = new Sub();
    //    println(user.name)
    //    user.abs()
    user.test1()
  }
}

//父类
abstract class Person {
  var name: String = _
  //属性没有初始化就是抽象属性
  var sex: String //public abstract String sex()
  //声明抽象方法 只有声明 没有实现
  val address: String = "xxx" //public abstract String sex()
  def abs()

  def test1(): Unit = {
    println("fulei ")
  }
}

// TODO:  继承父类的所有方法
class Sub extends Person {
  //重写父类的方法,补全即可
  override def abs(): Unit = {
    println("xxx")
  }

  override def test1(): Unit = {
    println("zilei ")
  }

  //重写属性 补全属性
  override var sex: String = _
  //属性可以被重写 但是不能重写父类var声明的变量，只能是val
  override val address: String = "yyy"
}
