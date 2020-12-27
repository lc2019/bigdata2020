package day04_mxdx

object classInfo {
  def main(args: Array[String]): Unit = {
    // TODO: predf 伴生对象 默认导入到当前开发环境中
    //     val value: Class[test] = classOf[test]
    //    println(value.getClasses.toString)
    //    //type 别名
    //    type bm = test
    //    new bm()
    var t = new test()
    // TODO: 类型判断
    val bool = t.isInstanceOf[test]
    if (bool) {
      //类中转换
      val test2 = bool.asInstanceOf[test]
    }
  }

}

class test {

}