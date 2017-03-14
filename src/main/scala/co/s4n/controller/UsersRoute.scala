package co.s4n.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.{ as, complete, entity, get, path, pathPrefix, post }
import co.s4n.entity.User
import co.s4n.service.UserService
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

/**
 * Created by seven4n on 20/02/17.
 */

case class UsersRoute(implicit val executionContext: ExecutionContext) extends Directives with SprayJsonSupport with DefaultJsonProtocol {
  def route: Route = {
    implicit val userFormat = jsonFormat4(User)
    pathPrefix("users") {
      get {
        path(LongNumber) { id =>
          onComplete(UserService.findUser(id)) {
            case Success(s) => s match {
              case Some(user) => complete(user)
              case None => complete(StatusCodes.NotFound)
            }
            case Failure(f) => complete(StatusCodes.InternalServerError)
          }
        }
      } ~
        (post & entity(as[User])) { user =>
          onComplete(UserService.saveUser(user)) {
            case Success(s) => s match {
              case Some(id) => complete(StatusCodes.Created)
              case None => complete(StatusCodes.NotFound)
            }
            case Failure(f) => complete(StatusCodes.InternalServerError)
          }
        } ~
        (put & entity(as[User])) { user =>
          path(LongNumber) { id =>
            onComplete(UserService.updateUser(id, user)) {
              case Success(s) => s match {
                case Some(_) => complete(StatusCodes.OK)
                case None => complete(StatusCodes.NotFound)
              }
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

