package co.s4n.user.service

import co.s4n.database.UsersDatabase
import co.s4n.user.entity.User
import com.outworkers.phantom.dsl.ResultSet

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object UserService extends UsersDatabase {

  def findUser(id: Long): Future[Option[User]] = {
    database.Users.findUser(id)
  }

  def saveUser(user: User): Future[ResultSet] = {
    database.Users.saveUser(user)
  }

  def updateUser(id: Long, user: User): Future[Option[User]] = {
    //    database.users.updateUser(user)
    Future {
      Option(user)
    }
  }

  def deleteUser(userId: Long): Future[Option[Boolean]] = {
    //    database.users.deleteUser(userId)
    Future {
      Option(true)
    }
  }

}

