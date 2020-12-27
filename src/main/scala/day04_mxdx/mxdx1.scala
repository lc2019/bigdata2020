package day04_mxdx

/**
 * todo 面向对象
 */
object mxdx1 {
  def main(args: Array[String]): Unit = {
    val user: User = new User();
    user.username = "lc"
    println(user.username)
  }
}

//类的声明
class User {
  //todo 声明属性 初始化 默认是私有的属性 提供了get和set方法
  //如果直接指明 private 则提供的是private的get和set 外部无法访问
  var username: String = null
  var age: Int = _
  //如果声明使用的是val 属性默认是私有的 只有get方法 没有set方法
  val email: String = ""

  def login(): Boolean = {
    true
  }
}
