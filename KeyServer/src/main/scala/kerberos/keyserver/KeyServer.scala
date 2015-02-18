package kerberos.keyserver

import akka.actor.{ActorSystem, DeadLetter, Props}
import kerberos.keyserver.actor.{KeyServerSupervisor, Listener}
import kerberos.util.IO

/**
 * Created by kasonchan on 1/29/15.
 */

case object Test

object KeyServer {
  //    Create key server system
  val system = ActorSystem("KeyServerSystem")
  
  def main(args: Array[String]) {
    //    Create event listener actor
    val listener = system.actorOf(Props(classOf[Listener]))
    system.eventStream.subscribe(listener, classOf[DeadLetter])

    //    Create key server actor
    val keyServerSupervisor = system.actorOf(Props[KeyServerSupervisor], name = "KeyServerSupervisor")
    val io = new IO(keyServerSupervisor)

    keyServerSupervisor ! "Key server is alive"

    keyServerSupervisor ! Test

    do {
    } while (io.input() != "exit")
  }
}
