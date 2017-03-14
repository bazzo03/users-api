package co.s4n.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ Directives, Route }
import co.s4n.entity.User
import co.s4n.service.UserService
import spray.json.DefaultJsonProtocol

import scala.util.{ Failure, Success }

/**
 * Created by seven4n on 20/02/17.
 */

object UsersRoute extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  //noinspection ScalaStyle
  def route: Route = {

    implicit val userFormat = jsonFormat4(User)

    pathPrefix("users") {
      get {
        path(LongNumber) { id =>
          onComplete(UserService.findUser(id)) {
            case Success(Some(user)) => complete(user)
            case Success(None) => complete(StatusCodes.NotFound)
            case Failure(f) => complete(StatusCodes.InternalServerError)
          }
        }
      } ~
        (post & entity(as[User])) { user =>
          onComplete(UserService.saveUser(user)) {
            case Success(Some(user)) => complete(StatusCodes.Created)
            case Success(None) => complete(StatusCodes.NotFound)
            case Failure(f) => complete(StatusCodes.InternalServerError)
          }
        } ~
        (put & entity(as[User])) { user =>
          path(LongNumber) { id =>
            onComplete(UserService.updateUser(id, user)) {
              case Success(Some(user)) => complete(StatusCodes.OK)
              case Success(None) => complete(StatusCodes.NotFound)
              case Failure(f) => complete(StatusCodes.InternalServerError)
            }
          }
        } ~
        delete {
          path(LongNumber) { id =>
            onComplete(UserService.deleteUser(id)) {
              case Success(s) => s match {
                case Some(_) => complete(StatusCodes.OK)
                case None => complete(StatusCodes.NotFound)
              }
              case Failure(f) => complete(StatusCodes.InternalServerError)
            }
          }
        }
    }
  }
}
