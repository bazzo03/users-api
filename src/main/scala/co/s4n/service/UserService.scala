package co.s4n.service

import co.s4n.entity.User
import co.s4n.repository2.UserDao

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object UserService {

  def findUser(id: Long): Future[Option[User]] = {
    Future {
      UserDao.findUser(id)
    }
  }

  def saveUser(user: User): Future[Option[Long]] = {
    Future {
      UserDao.saveUser(user)
    }
  }

  def updateUser(user: User): Future[Option[User]] = {
    Future {
      UserDao.updateUser(user)
    }
  }

  def deleteUser(user: User): Future[Option[Boolean]] = {
    Future {
      UserDao.deleteUser(user)
    }
  }

}

