package kerberos.util

import akka.actor.ActorRef
import kerberos.messages.SessionKeyRequest

import scala.io.StdIn._

case class test(x: Int)

/**
 * Created by kasonchan on 2/11/15.
 */
class IO(clientSupervisor: ActorRef) {
  //  Capital letters or small letters of exit to exit program
  val exit = """[eE][xX][iI][tT]""".r

  //  Service input patterns
  val add = """(\d+) ?\+ ?(\d+)""".r
  val subtract = """(\d+) ?\- ?(\d+)""".r
  val multiple = """(\d+) ?\* ?(\d+)""".r
  val divide = """(\d+) ?\/ ?(\d+)""".r

  def input() = {
    val input = readLine()

    input match {
      case m: String => {

        //        TODO: Implement messages
        m match {
          case add(num1, num2) => {
            println(num1 + " + " + num2)

            clientSupervisor ! SessionKeyRequest("A", "B")
          }
          case subtract(num1, num2) => {
            println(num1 + " - " + num2)

            clientSupervisor ! SessionKeyRequest("A", "B")
          }
          case multiple(num1, num2) => {
            println(num1 + " * " + num2)

            clientSupervisor ! SessionKeyRequest("A", "B")
          }
          case divide(num1, num2) => {
            println(num1 + " / " + num2)

            clientSupervisor ! SessionKeyRequest("A", "B")
          }
          case "test application" => {
            clientSupervisor ! "test application"
          }
          case exit() => {
            clientSupervisor ! "exit"
            "exit"
          }
          case x => {
            println("Error - undefined operation: " + x)
          }
        }
      }
      case x => {
        println("Error - undefined command: " + x)
      }
    }
  }
}
