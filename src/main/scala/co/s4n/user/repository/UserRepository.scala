package co.s4n.user.repository

import co.s4n.infrastructure.database.UsersDatabase
import co.s4n.user.entity.User
import com.outworkers.phantom.dsl.ResultSet

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }

object UserRepository extends UsersDatabase {

  def findUser(id: Long): Future[Option[User]] = {
    database.Users.findUser(id)
  }

  def findAllUsers(): Future[List[User]] = {
    database.Users.findAllUsers()
  }

  def saveUser(user: User): Future[ResultSet] = {
    database.Users.saveUser(user)
  }

  def updateUser(id: Long, user: User): Future[ResultSet] = {
    database.Users.saveUser(user)
  }

  def deleteUser(userId: Long): Future[ResultSet] = {
    database.Users.deleteUser(userId)
  }

}

