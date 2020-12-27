package day04_mxdx

//todo 四种权限 public protected private 包访问权限private[]
//public 默认权限 没有关键字
//protected 只能子类访问 同包访问不了
//private 只能当前类
object fwqx {
  def main(args: Array[String]): Unit = {

  }
}

class User2 {

}
package p1 {
  package p2 {

    class Userp2 {
      var username = "lc"
      //当前类才能访问
      private var password = "123"
      //子类才能访问
      protected var email = "lc@123.com"
      //p2下面的类才能访问
      private[p2] var address = "xxx"
    }

  }

  package p3 {

    import day04_mxdx.p1.p2.Userp2

    class emp3 {
      def test(): Unit = {
        val user = new Userp2;
        println(user.username)
      }
    }

  }

}