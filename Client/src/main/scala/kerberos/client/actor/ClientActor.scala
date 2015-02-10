package kerberos.client.actor

import akka.actor.Actor
import kerberos.encryption.{ElGamalPrivateKey, ElGamalPublicKey}
import kerberos.messages.{SessionKeyReply, SessionKeyRequest}

/**
 * Created by kasonchan on 1/29/15.
 */
class ClientActor extends Actor with akka.actor.ActorLogging {
  //  Create the remote key server actor
  val keyServerActor = context.actorSelection("akka.tcp://KeyServerSystem@127.0.0.1:2552/user/KeyServerActor")

  //  Client public and private keys
  val privateKey = ElGamalPrivateKey(2)
  val publicKey = ElGamalPublicKey(2357, 2351, 36)

  def receive = {
    case sessionKeyRequest: SessionKeyRequest => {
      log.info(sessionKeyRequest.toString)
      keyServerActor ! sessionKeyRequest
    }
    case sessionKeyReply: SessionKeyReply => {
      sessionKeyReply match {
        case SessionKeyReply(cid, sid, sessionKey, encryptedToken) =>
          log.info(sessionKeyReply.toString)
      }
    }
    case msg: String => {
      log.info(msg)
    }
  }
}
