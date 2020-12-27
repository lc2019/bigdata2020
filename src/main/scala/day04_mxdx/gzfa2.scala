package day04_mxdx


object gzfa2 {
  def main(args: Array[String]): Unit = {
    var user = new User1("123");
    println(user.name)
  }
}

//类构造方法的参数的作用域是整个类
class User1(var name: String) {

}
