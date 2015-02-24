package kerberos.client.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{ActorRefRoutee, Router, SmallestMailboxRoutingLogic}
import kerberos.messages._

/**
 * Created by kasonchan on 2/15/15.
 */

object ClientSupervisor {
  //  Application hostname and port
  def props(aHostname: String, aPort: String): Props =
    Props(new ClientActor(aHostname, aPort))
}

class ClientSupervisor(aHostname: String, aPort: String) extends Actor with
akka.actor.ActorLogging {
  //  Create router containing 5 routee of client actor
  var router: Router = {
    val routees: Vector[ActorRefRoutee] = Vector.fill(5) {
      val r: ActorRef = context.actorOf(Props[ClientActor])
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
    case serviceSessionKeyRequest: ServiceSessionKeyRequest => {
      val sessionKeyRequest = SessionKeyRequest(serviceSessionKeyRequest.CID,
        serviceSessionKeyRequest.SID)

      log.info(sender() + " " + sessionKeyRequest.toString)

      router.route(sessionKeyRequest, sender())
    }
    case sessionKeyReply: SessionKeyReply => {
      log.info(sender() + " " + sessionKeyReply.toString)

      router.route(sessionKeyReply, sender())
    }
    case decryptedSessionKeyReply: DecryptedSessionKeyReply => {
      log.info(sender() + " " + decryptedSessionKeyReply.toString)
    }
    case serviceRequest: ServiceRequest => {
      log.info(sender() + " " + serviceRequest.toString)

      router.route(serviceRequest, sender())
    }
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
