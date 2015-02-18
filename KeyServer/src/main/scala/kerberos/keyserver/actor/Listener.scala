package kerberos.keyserver.actor

import akka.actor.{DeadLetter, Actor}

/**
 * Created by kasonchan on 2/17/15.
 */
class Listener extends Actor with akka.actor.ActorLogging {
  def receive = {
    case d: DeadLetter => log.warning(d.toString)
  }
}
