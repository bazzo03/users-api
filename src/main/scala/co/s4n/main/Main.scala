package co.s4n.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import co.s4n.infrastructure.kafka.Consumer
import co.s4n.user.route.UsersRoute

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("users-rest-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    // Define route
    val route: Route = UsersRoute.route
    //    Consumer.run()

    // Startup, and listen for requests
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    StdIn.readLine()

    //Shutdown
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}

