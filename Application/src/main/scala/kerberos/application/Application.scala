package kerberos.application

import akka.actor.{ActorSystem, DeadLetter, Props}
import kerberos.application.actor.{ApplicationSupervisor, Listener}
import kerberos.util.IO

/**
 * Created by kasonchan on 2/16/15.
 */
object Application {
  //    Create actor system
  implicit val system = ActorSystem("ApplicationSystem")

  def main(args: Array[String]) {
    //    Create event listener actor
    val listener = system.actorOf(Props(classOf[Listener]))
    system.eventStream.subscribe(listener, classOf[DeadLetter])

    //    Create application supervisor actor
    val applicationSupervisor = system.actorOf(Props[ApplicationSupervisor],
      name = "ApplicationSupervisor")
    val io = new IO(applicationSupervisor)

    applicationSupervisor ! "Application server is alive"

    do {
    } while (io.input() != "exit")
  }
}
