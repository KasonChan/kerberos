package kerberos.keyserver

import akka.actor.{ActorSystem, Props}
import kerberos.keyserver.actor.KeyServerSupervisor
import kerberos.util.IO

/**
 * Created by kasonchan on 1/29/15.
 */

case object Test

object KeyServer {
  def main(args: Array[String]) {
    //    Create key server system
    val system = ActorSystem("KeyServerSystem")

    //    Create key server actor
    val keyServerSupervisor = system.actorOf(Props[KeyServerSupervisor], name = "KeyServerSupervisor")
    val io = new IO(keyServerSupervisor)

    keyServerSupervisor ! "Key server is alive"

    keyServerSupervisor ! Test

    do {
    } while (io.input() != "exit")
  }
}
