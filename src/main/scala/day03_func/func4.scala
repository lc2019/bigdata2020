package day03_func

object func4 {
  def main(args: Array[String]): Unit = {
    def !!(i: Int): Int = {
      if (i == 1) {
        1
      } else {
        i * !!(i - 1)
      }
    }

    println(!!(5))
  }
}
