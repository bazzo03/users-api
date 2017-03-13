package co.s4n.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.ActorMaterializer
import co.s4n.controller.UsersRoute
import co.s4n.entity.User
import spray.json.DefaultJsonProtocol

object Main extends SprayJsonSupport with DefaultJsonProtocol {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("my-system")
    implicit val userFormat = jsonFormat4(User)
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      UsersRoute().route

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  }

}

