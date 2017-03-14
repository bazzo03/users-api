package co.s4n.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import co.s4n.user.controller.UsersRoute
import spray.json.DefaultJsonProtocol

import scala.io.StdIn

object Main extends SprayJsonSupport with DefaultJsonProtocol {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("simple-rest-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    // Define route
    val route: Route = UsersRoute.route

    // Startup, and listen for requests
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    StdIn.readLine()

    //Shutdown
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}

