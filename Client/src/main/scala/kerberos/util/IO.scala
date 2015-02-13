package kerberos.util

import akka.actor.ActorRef
import kerberos.messages.Exit

import scala.io.StdIn._

/**
 * Created by kasonchan on 2/11/15.
 */
class IO(client: ActorRef) {
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
        client ! m
        
        //        TODO: Implement messages
        m match {
          case add(num1, num2) => println(num1 + " + " + num2)
          case subtract(num1, num2) => println(num1 + " - " + num2)
          case multiple(num1, num2) => println(num1 + " * " + num2)
          case divide(num1, num2) => println(num1 + " / " + num2)
          case exit() => {
            client ! Exit
            "exit"
          }
          case _ => println("Error - undefined operation")
        }
      }
      case x => {
        println("Error - undefined command")
      }
    }
  }
}
