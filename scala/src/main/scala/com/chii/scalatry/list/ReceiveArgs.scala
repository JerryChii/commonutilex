package com.chii.scalatry.list

/**
  * Describe:   
  * Author:  JerryChii.
  * Date:    2016/9/20
  */
object ReceiveArgs {
  private def parse(args: List[String]): Unit = args match {
    case ("--ip" | "i") :: value :: tail =>
      println(value)
      parse(tail)
    case ("--host" | "-h") :: value :: tail =>
      println(value)
      parse(tail)

    case ("--port" | "-p") :: IntParam(value) :: tail =>
      println(value)
      parse(tail)

    case "--webui-port" :: IntParam(value) :: tail =>
      println(value)
      parse(tail)

    case ("--properties-file") :: value :: tail =>
      println(value)
      parse(tail)

    case ("--help") :: tail =>
      println("some tips")
      parse(tail)

    case Nil => // No-op

    case _ =>
      System.exit(1)
  }

  /**
    * apply通常被称作注入方法
    * unapply通常被称为提取方法
    */
  private object IntParam {
    def unapply(str: String): Option[Int] = {
      try {
        Some(str.toInt)
      } catch {
        case e: NumberFormatException => None
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val args = Array("--ip", "127.0.0.1", "-p", "9092")
    parse(args.toList)
  }
}
