package kerberos.client.actor

import akka.actor.Actor

/**
 * Created by kasonchan on 1/29/15.
 */
class ClientActor extends Actor {
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://KeyServerSystem@127.0.0.1:2552/user/KeyServerActor")
  var counter = 0

  def receive = {
    case "START" => {
      remote ! "Hello from " + self.path.name
    }
    case msg: String => {
      println(self.path.name + s": '$msg'")
    }
  }
}
