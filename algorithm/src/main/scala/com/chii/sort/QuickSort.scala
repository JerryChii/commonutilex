package com.chii.sort

/**
  * Describe:   
  * Author:  JerryChii.
  * Date:    2016/8/22
  */
object QuickSort {
  def sort(ls: List[Int]): List[Int] = {
    ls match {
      case Nil => Nil
      case base :: tail => {
        val (lef, right) = tail.partition(_ < base)
        sort(lef) ::: base :: sort(right)
      }
    }
  }

  def main(args: Array[String]) {
    print(sort(List(4,3,5,2,20,1)))
  }
}
