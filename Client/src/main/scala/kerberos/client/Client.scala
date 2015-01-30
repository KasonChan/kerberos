package kerberos.client

import akka.actor.{ActorSystem, Props}
import kerberos.client.actor.ClientActor
import kerberos.messages.SessionKeyRequest

/**
 * Created by kasonchan on 1/29/15.
 */
object Client {
  def main(args: Array[String]) {
    //    Create actor system
    implicit val system = ActorSystem("ClientSystem")

    //    Create client actor
    val clientActor = system.actorOf(Props[ClientActor], name = "ClientActor")

    clientActor ! SessionKeyRequest("Alice", "Bob")
  }
}
