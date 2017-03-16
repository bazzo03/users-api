package co.s4n.user.route

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, StatusCodes }
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{ Matchers, WordSpec }

/**
 * Created by seven4n on 15/03/17.
 */
class UsersRouteSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "UsersRoute" should {

    "create a new user" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |  "id": 9999,
           |  "name": "Carlos",
           |  "lastname": "Gonzalez",
           |  "ip": "127.0.0.1"
           |}
        """.stripMargin
      )
      val httpEntity = HttpEntity(ContentTypes.`application/json`, jsonRequest)
      Post("/users", httpEntity) ~> UsersRoute.route ~> check {
        status === StatusCodes.Created
      }
    }

    "create a new user 1" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |  "id": 9998,
           |  "name": "Pepito",
           |  "lastname": "Perez",
           |  "ip": "127.0.0.1"
           |}
        """.stripMargin
      )
      val httpEntity = HttpEntity(ContentTypes.`application/json`, jsonRequest)
      Post("/users", httpEntity) ~> UsersRoute.route ~> check {
        status === StatusCodes.Created
      }
    }

    "return a user for GET requests with a given id" in {
      Get("/users/9999") ~> UsersRoute.route ~> check {
        responseAs[String] contains "id : 9999, name : Carlos"
      }
    }

    "return all users for GET requests when no Id is found" in {
      Get("/users") ~> UsersRoute.route ~> check {
        responseAs[String] contains "id : 9999, name : Carlos"
        responseAs[String] contains "id : 9998, name : Pepito"
      }
    }

    "Deletes a user" in {
      Delete("/users/9999") ~> UsersRoute.route ~> check {
        status === StatusCodes.OK
      }
    }

    "Deletes a user2" in {
      Delete("/users/9998") ~> UsersRoute.route ~> check {
        status === StatusCodes.OK
      }
    }

  }

}

