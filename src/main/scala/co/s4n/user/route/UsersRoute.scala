package co.s4n.user.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ StatusCode, StatusCodes }
import akka.http.scaladsl.server.Directives
import co.s4n.infrastructure.kafka.Producer
import co.s4n.user.entity.User
import co.s4n.user.repository.UserRepository
import com.outworkers.phantom.dsl.ResultSet
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by seven4n on 20/02/17.
 */

object UsersRoute extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  //noinspection ScalaStyle
  def route = {

    implicit val userFormat = jsonFormat4(User)

    pathPrefix("users") {
      get {
        path(LongNumber) { id =>
          val future: Future[Option[User]] = UserRepository.findUser(id)
          val response: Future[(StatusCode, User)] = future.map {
            case Some(user) =>
              Producer.produceKafka("User was consulted: " + user)
              (StatusCodes.OK, user)
            case None =>
              Producer.produceKafka("Attempt to consult user, but no user found. Id was : " + id)
              (StatusCodes.NotFound, User(0, "", "", ""))
          }
          complete(response)
        }
      } ~
        get {
          val future: Future[List[User]] = UserRepository.findAllUsers()
          val response: Future[(StatusCode, List[User])] = future.map {
            case x :: xs =>
              Producer.produceKafka("All the users were consulted")
              (StatusCodes.OK, x :: xs)
            case Nil =>
              Producer.produceKafka("Attempt to consult all the users. None was found")
              (StatusCodes.NotFound, Nil)
          }
          complete(response)
        } ~
        (post & entity(as[User])) { user =>
          val future: Future[ResultSet] = UserRepository.saveUser(user)
          val response = future.map {
            Producer.produceKafka("User was created: " + user)
            _ => StatusCodes.Created
          }
          complete(response)
        } ~
        (put & entity(as[User])) { user =>
          path(LongNumber) { id =>
            val future = UserRepository.updateUser(id, user)
            val response = future.map {
              Producer.produceKafka("User was updated: " + user)
              _ => StatusCodes.OK
            }
            complete(response)
          }
        } ~
        delete {
          path(LongNumber) { id =>
            val future = UserRepository.deleteUser(id)
            val response = future.map {
              Producer.produceKafka("User was deleted. Id was: " + id)
              _ => StatusCodes.OK
            }
            complete(response)
          }
        }
    }
  }
}