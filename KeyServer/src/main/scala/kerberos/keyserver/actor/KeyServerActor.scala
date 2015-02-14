package kerberos.keyserver.actor

import akka.actor.Actor
import kerberos.encryption.{ElGamal, ElGamalPrivateKey, ElGamalPublicKey}
import kerberos.messages.{EncryptedSessionKey, EncryptedToken, SessionKeyReply, SessionKeyRequest}


/**
 * Created by kasonchan on 1/29/15.
 */
object KeyServerActor {

  case object Exit

}

class KeyServerActor extends Actor with akka.actor.ActorLogging with ElGamal {
  //  Client public and private keys
  val clientPublicKey = ElGamalPublicKey(1579, 1571, 677)
  val clientPrivateKey = ElGamalPrivateKey(11)

  //  Application public and private keys
  val applicationPublicKey = ElGamalPublicKey(1327, 1321, 426)
  val applicationPrivateKey = ElGamalPrivateKey(17)

  def receive = {
    case sessionKeyRequest: SessionKeyRequest => {
      sessionKeyRequest match {
        case SessionKeyRequest(cid, sid) => {
          //          TODO: Add database
          log.info(sessionKeyRequest.toString)

          //          Construct encrypted token
          val bCID = stringToBigInt(cid) // BigInt cid
          val bSID = stringToBigInt(sid) // BigInt sid

          val eTCID = ElGamal_EncryptMessage(applicationPublicKey, bCID)
          val eTSID = ElGamal_EncryptMessage(applicationPublicKey, bSID)

          val tSessionKey = clientPublicKey

          val eTSessionKeyP = ElGamal_Encrypt(applicationPublicKey, tSessionKey.p)
          val eTSessionKeyAlpha = ElGamal_Encrypt(applicationPublicKey, tSessionKey.alpha)
          val eTSessionKeyAA = ElGamal_Encrypt(applicationPublicKey, tSessionKey.aa)

          val eTSessionKey = EncryptedSessionKey(eTSessionKeyP, eTSessionKeyAlpha, eTSessionKeyAA)

          //          Encrypt token
          val et = EncryptedToken(eTCID, eTSID, eTSessionKey)

          //          Construct session key reply
          val eCID = ElGamal_EncryptMessage(clientPublicKey, bCID)
          val eSID = ElGamal_EncryptMessage(clientPublicKey, bSID)

          val sessionKey = applicationPublicKey

          val eSessionKeyP = ElGamal_Encrypt(clientPublicKey, sessionKey.p)
          val eSessionKeyAlpha = ElGamal_Encrypt(clientPublicKey, sessionKey.alpha)
          val eSessionKeyAA = ElGamal_Encrypt(clientPublicKey, sessionKey.aa)

          val eSessionKey = EncryptedSessionKey(eSessionKeyP, eSessionKeyAlpha, eSessionKeyAA)

          //          Encrypt session key reply
          val er = SessionKeyReply(eCID, eSID, eSessionKey, et)

          sender() ! er

          log.info(er.toString)
        }
      }
    }
    case msg: String => {
      log.info(msg)
    }
    case KeyServerActor.Exit => {
      log.info("Exit")
      context.system.shutdown()
    }
  }
}
