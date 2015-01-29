package kerberos.keyserver.actor

import akka.actor.Actor

/**
 * Created by kasonchan on 1/29/15.
 */
class KeyServerActor extends Actor {
  def receive = {
    case msg: String => {
      println(s"Key server received message: '$msg'")
      sender() ! "Hello from the key server"
    }
  }
}
