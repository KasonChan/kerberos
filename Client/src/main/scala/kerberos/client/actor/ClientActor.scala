package kerberos.client.actor

import akka.actor.{Actor, Props}
import kerberos.encryption.{ElGamal, ElGamalPrivateKey, ElGamalPublicKey}
import kerberos.messages.{Exit, SessionKeyReply, SessionKeyRequest}

/**
 * Created by kasonchan on 1/29/15.
 */
object ClientActor {
  //  Application hostname and port
  def props(aHostname: String, aPort: String): Props =
    Props(new ClientActor(aHostname, aPort))
}


class ClientActor(aHostname: String, aPort: String) extends Actor
with akka.actor.ActorLogging with ElGamal {
  //  Create the remote key server actor
  val keyServerActor =
    context.actorSelection("akka.tcp://KeyServerSystem@127.0.0.1:2552/user/KeyServerActor")

  //  Client public and private keys
  val publicKey = ElGamalPublicKey(1579, 1571, 677)
  val privateKey = ElGamalPrivateKey(11)

  def receive = {
    case sessionKeyRequest: SessionKeyRequest => {
      log.info(sessionKeyRequest.toString)
      keyServerActor ! sessionKeyRequest
    }
    case sessionKeyReply: SessionKeyReply => {
      sessionKeyReply match {
        case SessionKeyReply(cid, sid, sessionKey, encryptedToken) =>
          log.info(sessionKeyReply.toString)

          val dCID = ElGamal_DecryptMessage(publicKey, privateKey, cid)
          val dSID = ElGamal_DecryptMessage(publicKey, privateKey, sid)

          val dSessionKeyP = ElGamal_Decrypt(publicKey, privateKey, sessionKey.p)
          val dSessionKeyAlpha = ElGamal_Decrypt(publicKey, privateKey, sessionKey.alpha)
          val dSessionKeyAA = ElGamal_Decrypt(publicKey, privateKey, sessionKey.aa)

          val dSessionKey = ElGamalPublicKey(dSessionKeyP, dSessionKeyAlpha, dSessionKeyAA)

          log.info(dCID + " " + dSID + " " + dSessionKey)
      }
    }
    case msg: String => {
      log.info(msg)
    }
    case Exit => {
      log.info("Exit")
      context.system.shutdown()
    }
  }
}
