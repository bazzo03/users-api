package co.s4n.user.route

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives
import co.s4n.user.entity._
import co.s4n.user.repository.UserRepository
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.Future
import scala.util.{Failure, Success}

object UsersRoute extends Directives {

  import scala.concurrent.ExecutionContext.Implicits.global

  //noinspection ScalaStyle
  def route = {
    pathPrefix("users") {
      /*get {
        path(LongNumber) { id =>
          onComplete(UserRepository.findUser(id)) {
            case Success(Some(user)) => complete(user)
            case Success(None) => complete(StatusCodes.NotFound)
            case Failure(_) => complete(StatusCodes.InternalServerError)
          }
        }
      }*/
      get {
        path(LongNumber) { id =>
          val f: Future[Option[User]] = UserRepository.findUser(id)
          val r: Future[(StatusCode, UserServiceProtocol)] = f.map {
            case Some(u) => (StatusCodes.OK, UserFound(u))
            case None => (StatusCodes.NotFound, UserNotFound(""))
          }.recover {
            case e: Exception => (StatusCodes.InternalServerError, DatabaseConnectionFailed(""))
          }
          complete(r)
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
