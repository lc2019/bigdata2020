package day03_func

object func3 {
  def main(args: Array[String]): Unit = {
    //    def f(): Unit = {
    //      println("func")
    //    }

    //    def f0() ={
    //      //todo 直接返回函数 此处 f= f() 执行结果返回 因为小括号省略了
    //      f
    //    }
    //    f0()

    //    def f0() = {
    //      //todo 直接返回函数本身而不是函数的执行结果
    //      f _
    //    }
    //    // todo 调用函数
    //    f0()()


    //    def f1() = {
    //      def f2(): Unit = {
    //        println("xxx")
    //      }
    //
    //      f2 _
    //    }
    //
    //    f1()()

    //    def f1(i: Int) = {
    //      def f2(j: Int): Int = {
    //        i * j
    //      }
    //
    //      f2 _
    //    }

    println(f1(2)(3))

    //todo 柯里化
    def f3(i: Int)(j: Int): Int = {
      i * j
    }

    println(f3(10)(2))

    //todo 闭包 延长了生命周期 将外部的变量引入到函数内部 改变了生命周期--->闭包
    def f1(i: Int) = {
      def f2(j: Int): Int = {
        i * j
      }

      f2 _
    }

    // TODO: 函数作为参数传递() => Int 参数列表 => 返回值类型
    def f4(fx: () => Int): Int = {
      fx() + 10
    }

    def f5(): Int = {
      5
    }

    println(f4(f5))

    //todo 匿名函数
    def f6(f: () => Unit): Unit = {
      f()
    }

    f6(() => {
      println("haha 匿名函数")
    })
  }
}
