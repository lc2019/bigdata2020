package day03_func

object exception {
  def main(args: Array[String]): Unit = {
    try {
      val i = 10
      val j = 0
      val k = i / j
    } catch {
      case ex: ArithmeticException => println("捕获异常了")
      case ex: Exception => println("warn")
    } finally {
      println("finnally")
    }
  }
}
