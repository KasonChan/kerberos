package kerberos.keyserver.actor

import akka.actor.Actor
import kerberos.encryption.{ElGamalPrivateKey, ElGamalPublicKey}
import kerberos.messages.{EncryptedToken, SessionKeyReply, SessionKeyRequest}

/**
 * Created by kasonchan on 1/29/15.
 */
class KeyServerActor extends Actor with akka.actor.ActorLogging {
  //  Client public and private keys
  val clientPublicKey = ElGamalPublicKey(2357, 2351, 36)
  val clientPrivateKey = ElGamalPrivateKey(2)

  //  Application public and private keys
  val applicationPublicKey = ElGamalPublicKey(1327,1321,426)
  val applicationPrivateKey = ElGamalPrivateKey(17)

  def receive = {
    case sessionKeyRequest: SessionKeyRequest => {
      sessionKeyRequest match {
        case SessionKeyRequest(cid, sid) => {
          //          TODO: Add database
          log.info(sessionKeyRequest.toString)

          val sessionKey = 139

          //          Construct encrypted token
          val t = EncryptedToken(cid, sid, 139)

          //          TODO: Encrypt token
          val et = EncryptedToken(t.CID, t.SID, t.SessionKey)

          //          Construct session key reply
          val r = SessionKeyReply(cid, sid, sessionKey, et)

          //          TODO: Encrypt session key reply
          val er = SessionKeyReply(cid, sid, sessionKey, et)

          sender() ! er

          log.info(er.toString)
        }
      }
    }
    case msg: String => {
      log.info(msg)
    }
  }
}
