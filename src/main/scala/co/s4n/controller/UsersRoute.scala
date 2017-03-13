package co.s4n.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ Directives, Route }
import co.s4n.entity.User
import co.s4n.service.UserService
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

/**
 * Created by seven4n on 20/02/17.
 */
//noinspection ScalaStyle
case class UsersRoute(implicit val executionContext: ExecutionContext) extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  def route: Route = {

    implicit val userFormat = jsonFormat4(User)

    pathPrefix("users") {
      get {
        path("get" / LongNumber) { id =>
          onComplete(UserService.findUser(id)) {
            case Success(s) => s match {
              case Some(user) => complete(StatusCodes.OK)
              case None => complete(StatusCodes.NotFound)
            }
            case Failure(f) => complete(StatusCodes.NotFound)
          }
        }
      } ~
        (post & entity(as[User])) { user =>
          path("create") {
            onComplete(UserService.saveUser(user)) {
              case Success(s) => s match {
                case Some(id) => complete(StatusCodes.Created)
                case None => complete(StatusCodes.NotFound)
              }
              case Failure(f) => complete(StatusCodes.NotFound)
            }
          }
        } ~
        (put & entity(as[User])) { user =>
          path("update") {
            onComplete(UserService.saveUser(user)) {
              case Success(s) => s match {
                case Some(id) => complete(StatusCodes.Created)
                case None => complete(StatusCodes.NotFound)
              }
              case Failure(f) => complete(StatusCodes.NotFound)
            }
          }
        } ~
        (delete & entity(as[User])) { user =>
          path("delete") {
            onComplete(UserService.saveUser(user)) {
              case Success(s) => s match {
                case Some(id) => complete(StatusCodes.Created)
                case None => complete(StatusCodes.NotFound)
              }
              case Failure(f) => complete(StatusCodes.NotFound)
            }
          }
        }
    }
  }
}

