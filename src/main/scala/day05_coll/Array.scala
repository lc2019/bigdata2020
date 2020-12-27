package day05_coll

import scala.collection.mutable.ArrayBuffer

object array {
  def main(args: Array[String]): Unit = {
    // TODO: list 有序 可重复  set 无序 不可重复 map key不能重复
    // TODO: scala集合 可变--不可变
    val ints: Array[Int] = Array(1, 2, 3, 4)
    //元素
    println(ints(0))
    //长度
    println(ints.length)

    //数组转换成字符串打印
    println(ints.mkString(","))

    //遍历元素
    for (elem <- ints) {
      println(elem)
    }
    // TODO: 只有1个参数的返回值Unit
    ints.foreach(println)

    //todo 修改数据
    ints.update(1, 5)

    //增加元素 产生新的数组 不会对元数组产生影响
    // TODO: 向数组后面加
    val ints1: Array[Int] = ints :+ 5
    // TODO: 向数组前面加 
    val ints2: Array[Int] = 5 +: ints
    println(ints.mkString == ints1.mkString)
    println(ints.mkString(",") + "===>" + ints1.mkString(",") + "===>" + ints2.mkString(","))

    // TODO: 可变数组
    val arrayBuffer = ArrayBuffer(5, 6, 7, 8)
    // TODO: 增加
    arrayBuffer.insert(4, 9)
    val buffer = arrayBuffer += 10
    // TODO: 修改
    arrayBuffer(0) = 10
    // TODO: 遍历
    arrayBuffer.foreach(println)

    println(buffer == arrayBuffer) //true

  }
}
