package co.s4n.user.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ Directives, Route }
import co.s4n.user.entity.User
import co.s4n.user.repository.UserRepository
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
          onComplete(UserRepository.findUser(id)) {
            case Success(Some(user)) => complete(user)
            case Success(None) => complete(StatusCodes.NotFound)
            case Failure(_) => complete(StatusCodes.InternalServerError)
          }
        }
      }
      get {
        path(LongNumber) { id =>
          onComplete(UserRepository.findUser(id)) {
            case Success(Some(user)) => complete(user)
            case Success(None) => complete(StatusCodes.NotFound)
            case Failure(_) => complete(StatusCodes.InternalServerError)
          }
        }
      } ~
        get {
          onComplete(UserRepository.findAllUsers()) {
            case Success(users) => complete(users)
            case Failure(_) => complete(StatusCodes.InternalServerError)
          }
        } ~
        (post & entity(as[User])) { user =>
          onComplete(UserRepository.saveUser(user)) {
            case Success(_) => complete(StatusCodes.Created)
            case Failure(_) => complete(StatusCodes.InternalServerError)
          }
        } ~
        (put & entity(as[User])) { user =>
          path(LongNumber) { id =>
            onComplete(UserRepository.updateUser(id, user)) {
              case Success(_) => complete(StatusCodes.OK)
              case Failure(_) => complete(StatusCodes.InternalServerError)
            }
          }
        } ~
        delete {
          path(LongNumber) { id =>
            onComplete(UserRepository.deleteUser(id)) {
              case Success(_) => complete(StatusCodes.OK)
              case Failure(_) => complete(StatusCodes.InternalServerError)
            }
          }
        }
    }
  }
}
