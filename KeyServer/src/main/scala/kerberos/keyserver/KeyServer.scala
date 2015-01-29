package kerberos.keyserver

import akka.actor.{ActorSystem, Props}
import kerberos.keyserver.actor.KeyServerActor

/**
 * Created by kasonchan on 1/29/15.
 */
object KeyServer {
  def main(args: Array[String]) {
    val system = ActorSystem("KeyServerSystem")
    val keyServerActor = system.actorOf(Props[KeyServerActor], name = "KeyServerActor")
    keyServerActor ! "The RemoteActor is alive"
  }
}
