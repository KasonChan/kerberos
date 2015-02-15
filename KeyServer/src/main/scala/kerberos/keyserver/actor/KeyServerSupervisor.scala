package kerberos.keyserver.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{ActorRefRoutee, Router, SmallestMailboxRoutingLogic}
import kerberos.messages.{SessionKeyReply, SessionKeyRequest}

/**
 * Created by kasonchan on 2/15/15.
 */
class KeyServerSupervisor extends Actor with akka.actor.ActorLogging {
  //  Create router containing 5 routee of key server actor
  // using smallest mail box routing logic
  var router: Router = {
    val routees: Vector[ActorRefRoutee] = Vector.fill(5) {
      val r: ActorRef = context.actorOf(Props[KeyServerActor])
      context watch r
      ActorRefRoutee(r)
    }
    Router(SmallestMailboxRoutingLogic(), routees)
  }

  override def preStart(): Unit = {
    log.info("Pre-start")
  }

  override def postStop(): Unit = {
    log.info("Post-stop")
  }

  def receive = {
    case sessionKeyRequest: SessionKeyRequest => {
      log.info(sessionKeyRequest.toString)
      router.route(sessionKeyRequest, sender())
    }
    case sessionKeyReply: SessionKeyReply => {
      log.info(sessionKeyReply.toString)
      router.route(sessionKeyReply, sender())
    }
    case msg: String => {
      msg match {
        case "exit" =>
          log.info(msg)
          context.system.shutdown()
        case x =>
          log.warning("Undefined operation: " + x)
      }
    }
    case x => {
      log.warning("Undefined operation: " + x.toString)
    }
  }
}
