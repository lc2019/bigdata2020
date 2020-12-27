package day04_mxdx

// TODO: 隐式转换的类型 要注意转换的范围 byte-short-int-long.隐式转换有默认值 如果找不到会使用默认值 没有默认值会报错 
object trans2 {
  def main(args: Array[String]): Unit = {
    //隐式值
    implicit val username: String = "lulu"

    //隐式参数
    def test(implicit name: String = "lc"): Unit = {
      println("jiayou " + name)
    }

    test //使用隐式值
    test() //使用隐式参数
  }
}
