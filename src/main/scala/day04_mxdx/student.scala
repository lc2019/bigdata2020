package day04_mxdx

//todo 伴生类-成员
class student {
  private val name = "lc"
}

//todo 伴生类对象-静态
//半生类对象可以访问半生类的私有属性 和方法
object student {
  def test(): Unit = {
    //    new student().name
    //伴生类对象来创建伴生类
    def apply(): student = new student()
  }
}
