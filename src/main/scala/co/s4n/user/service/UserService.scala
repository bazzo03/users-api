package co.s4n.user.service

import co.s4n.infrastructure.database.{ UserDataBase, UserDbProvider }
import co.s4n.user.entity.User
import co.s4n.user.repository.UserRepository
import com.outworkers.phantom.dsl.ResultSet

import scala.concurrent.Future

/**
 * Created by seven4n on 23/03/17.
 */
object UserService extends UserDbProvider with UserDataBase.Connector {

  def findUser(id: Long): Future[Option[User]] = {
    UserRepository.findUser(id)
  }

  def findAllUsers(): Future[List[User]] = {
    UserRepository.findAllUsers()
  }

  def saveUser(user: User): Future[ResultSet] = {
    UserRepository.saveUser(user)
  }

  def updateUser(id: Long, user: User): Future[ResultSet] = {
    UserRepository.saveUser(user)
  }

  def deleteUser(userId: Long): Future[ResultSet] = {
    UserRepository.deleteUser(userId)
  }

}
