package kerberos.application.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{ActorRefRoutee, Router, SmallestMailboxRoutingLogic}

/**
 * Created by kasonchan on 2/16/15.
 */
class ApplicationSupervisor extends Actor with akka.actor.ActorLogging {
  //  Create router containing 5 routee of application actor
  // using smallest mail box routing logic
  var router: Router = {
    val routees: Vector[ActorRefRoutee] = Vector.fill(5) {
      val r: ActorRef = context.actorOf(Props[ApplicationActor])
      context watch r
      ActorRefRoutee(r)
    }
    Router(SmallestMailboxRoutingLogic(), routees)
  }

  def receive = {
    //    TODO: Add service request and reply
    case msg: String => {
      msg match {
        case "exit" =>
          log.info(msg)
          context.system.shutdown()
        case "test application" =>
          log.info(msg)
          router.route(msg, sender())
        case x =>
          log.warning("Undefined operation: " + sender() + " " + x)
      }
    }
    case x => {
      log.warning("Undefined operation: " + sender() + " " + x.toString)
    }
  }
}
