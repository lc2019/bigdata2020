package day04_mxdx

// TODO: 隐式类 类似包装类
object yinshilei {
  def main(args: Array[String]): Unit = {
    //隐式传递person对象
    val user1 = new User()
    user1.insert()
    user1.delete()
  }

  implicit class Person(u: User) {
    def delete(): Unit = {

    }
  }

  class User() {
    def insert() {

    }
  }

}
