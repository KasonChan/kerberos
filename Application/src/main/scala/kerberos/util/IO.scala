package kerberos.util

import akka.actor.ActorRef

import scala.io.StdIn._

/**
 * Created by kasonchan on 2/16/15.
 */
class IO(applicationSupervisor: ActorRef) {
  //  Capital letters or small letters of exit to exit program
  val exit = """[eE][xX][iI][tT]""".r

  def input() = {
    val input = readLine()

    input match {
      case m: String => {
        m match {
          case exit() =>
            applicationSupervisor ! "exit"
            "exit"
          case x =>
            println("Error - undefined command: " + x)
        }
      }
      case x => {
        println("Error - undefined command: " + x)
      }
    }
  }
}
