package com.chii.scalatry.list

/**
  * Describe: 快排
  * Author:  JerryChii.
  * Date:    2016/9/20
  */
object QuickSort {
  def sort(ls: List[Int]): List[Int] = {
    ls match {
      case Nil => Nil
      case base :: tail => {
        val (left, right) = tail.partition(_ < base)
        sort(left) ::: base :: sort(right)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    sort(List(4, 3, 5, 2, 20, 1))
  }
}
