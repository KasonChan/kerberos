package kerberos.client

import akka.actor.ActorSystem
import kerberos.client.actor.ClientSupervisor
import kerberos.messages.SessionKeyRequest
import kerberos.util.IO

/**
 * Created by kasonchan on 1/29/15.
 */
object Client {
  //    Create actor system
  implicit val system = ActorSystem("ClientSystem")

  def main(args: Array[String]) {
    args.size match {
      case 0 => {
        //        Create default client supervisor actor
        //        Application server host name 127.0.0.1, port number 2554
        val clientSupervisor = system.actorOf(ClientSupervisor.props("127.0.0.1", "2554"), name = "ClientSupervisor")
        val io = new IO(clientSupervisor)

        clientSupervisor ! SessionKeyRequest("A", "B")

        do {
        } while (io.input() != "exit")
      }
      case 2 => {
        //        Create client supervisor actor
        //        Application server host name args(0), port number args(1)
        val clientSupervisor = system.actorOf(ClientSupervisor.props(args(0), args(1)), name = "ClientSupervisor")
        val io = new IO(clientSupervisor)

        clientSupervisor ! SessionKeyRequest("A", "B")

        do {
        } while (io.input() != "exit")
      }
      case _ => {
        println("Error - invalid number of arguments")
        System.exit(-1)
      }
    }
  }
}
