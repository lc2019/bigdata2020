package day05_coll

import scala.collection.mutable

object method3 {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4)
    // TODO:  reduce逻辑可以自己定义
    //    println(list.reduce(_ + _)) //20

    // TODO:  1.reverse 4,3,2,1    1-2-3-(4-10)  始终右边减左边
    val i = list.foldRight(10)(_ - _) //8
    println(i)
    // TODO:  第二部分是自定义的封装逻辑 始终左边减右边
    //10-4-3-2-1
    //    val i = list.foldLeft(10)(_-_) //0


    // TODO:  fold对map的操作
    val map1 = mutable.Map("a" -> 1, "b" -> 2, "c" -> 3)
    val map2 = mutable.Map("a" -> 1, "c" -> 2, "d" -> 3)
    val stringToInt: mutable.Map[String, Int] = map1.foldLeft(map2)((map, t) => {
      //k,v  a,2 类似java的map  如果没有取0 ——t.2 如果右取值+t.2
      map(t._1) = map.getOrElse(t._1, 0) + t._2
      map
    })
    println(stringToInt)
  }
}
