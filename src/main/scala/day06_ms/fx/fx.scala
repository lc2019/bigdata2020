package day06_ms.fx

object fx {
  def main(args: Array[String]): Unit = {
    test(new child)

    // TODO:  scala提供协变的功能 +比声明的多 -比声明的少
    //    val user :stu[User] =new stu[child]
    val user: stu[User] = new stu[Person]
  }

  // TODO: <: 表示泛型的上限 所以传递的类应该是子类或当前类 
  def test[T <: User](t: T): Unit = {
    println(t)
  }
}

class Person {

}

class User extends Person {

}

class child extends User {

}

//class stu[+User]{
//
//}

class stu[-User] {

}