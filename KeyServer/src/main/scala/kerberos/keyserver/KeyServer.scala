package kerberos.keyserver

import akka.actor.{ActorSystem, Props}
import kerberos.keyserver.actor.KeyServerActor
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
    val keyServerActor = system.actorOf(Props[KeyServerActor], name = "KeyServerActor")
    val io = new IO(keyServerActor)

    keyServerActor ! "Key server is alive"

    keyServerActor ! Test
    
    do {
    } while (io.input() != "exit")
  }
}
