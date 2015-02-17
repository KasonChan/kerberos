package kerberos.application.actor

import akka.actor.Actor
import kerberos.encryption.{ElGamal, ElGamalPrivateKey, ElGamalPublicKey}

/**
 * Created by kasonchan on 2/16/15.
 */
class ApplicationActor extends Actor with akka.actor.ActorLogging with ElGamal {
  //  Application public and private keys
  val applicationPublicKey = ElGamalPublicKey(1327, 1321, 426)
  val applicationPrivateKey = ElGamalPrivateKey(17)

  override def preStart(): Unit = {
    log.info("Pre-start")
  }

  override def postStop(): Unit = {
    log.info("Post-stop")
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
        case x =>
          log.warning("Undefined operation: " + sender() + " " + x)
      }
    }
    case x => {
      log.warning("Undefined operation: " + sender() + " " + x.toString)
    }
  }
}
