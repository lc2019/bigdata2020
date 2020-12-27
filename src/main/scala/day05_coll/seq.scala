package day05_coll

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object seq {
  def main(args: Array[String]): Unit = {
    // TODO: 序列
    val list = List(1, 2, 3, 4)
    // TODO: 添加1个
    val ints = list :+ 1
    // TODO: 添加1个 list
    val list1 = list ++ ints
    val list2 = 7 :: 8 :: list1
    // TODO: :::将list的元素取出
    val list3 = 7 :: 8 :: list1 ::: list2
    println(list2.mkString(","))
    println(list3.mkString(","))

    // TODO: 更改 
    val list4 = list3.updated(1, 9)
    list4.foreach(println)
    // TODO: 删除
    val list5 = list4.drop(0)
    println(list5)

    // TODO: Listbuffer 可变集合
    val lb = ListBuffer(1, 2, 3, 4)
    lb.insert(0, 5)
    lb.update(1, 6)
    lb.drop(5)
    lb.remove(3)
    println(lb.head)
    println(lb.tail)
    println(lb.last)
    println(lb.init)
    // TODO: 可变集合 
    val q = mutable.Queue(1, 2, 3, 4)
    q.enqueue(5)
    println(q)
    q.dequeue()
    println(q)

  }
}
