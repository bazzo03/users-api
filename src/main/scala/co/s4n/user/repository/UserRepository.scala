package co.s4n.user.repository

import co.s4n.infrastructure.database.{ UserDataBase, UserDbProvider }
import co.s4n.user.entity.User
import com.outworkers.phantom.database.DatabaseProvider
import com.outworkers.phantom.dsl.ResultSet

import scala.concurrent.Future

object UserRepository extends UserDbProvider with UserDataBase.Connector {

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

