package kerberos.application.actor

import akka.actor.Actor
import kerberos.encryption.{ElGamal, ElGamalPrivateKey, ElGamalPublicKey}
import kerberos.messages.ServiceRequest

/**
 * Created by kasonchan on 2/16/15.
 */
class ApplicationActor extends Actor with akka.actor.ActorLogging with ElGamal {
  //  Application public and private keys
  val publicKey = ElGamalPublicKey(1327, 1321, 426)
  val privateKey = ElGamalPrivateKey(17)

  override def preStart(): Unit = {
    log.info("Pre-start")
  }

  override def postStop(): Unit = {
    log.info("Post-stop")
  }

  def receive = {
    //    TODO: Add service request and reply
    case serviceRequest: ServiceRequest => {
      serviceRequest match {
        case ServiceRequest(cid, service, encryptedToken) => {
          log.info(sender() + " " + serviceRequest.toString)

          val dEncryptedTokenCID = ElGamal_DecryptMessage(publicKey, privateKey, encryptedToken.CID)
          val dEncryptedTokenSID = ElGamal_DecryptMessage(publicKey, privateKey, encryptedToken.SID)

          val dSessionKeyP = ElGamal_Decrypt(publicKey, privateKey, encryptedToken.SessionKey.p)
          val dSessionKeyAlpha = ElGamal_Decrypt(publicKey, privateKey, encryptedToken.SessionKey.alpha)
          val dSessionKeyAA = ElGamal_Decrypt(publicKey, privateKey, encryptedToken.SessionKey.aa)

          log.info(dEncryptedTokenCID + " " + dEncryptedTokenSID + " " +
            dSessionKeyP + " " + dSessionKeyAlpha + " " + dSessionKeyAA)
        }
      }
    }
    case msg: String => {
      msg match {
        case "exit" =>
          log.info(msg)
          context.system.shutdown()
        case "test application" =>
          log.info(sender() + " " + msg)
        case x =>
          log.warning("Undefined operation: " + sender() + " " + x)
      }
    }
    case x => {
      log.warning("Undefined operation: " + sender() + " " + x.toString)
    }
  }
}
