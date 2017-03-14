package co.s4n.user.repository

import co.s4n.user.entity.User

/**
 * Created by seven4n on 13/03/17.
 */
object UserRepository {

  def saveUser(user: User): Option[Long] = Option(user.id)

  def findUser(id: Long): Option[User] = Option(User(1, "Daniel", "Bernal", "127.0.0.1"))

  def updateUser(user: User): Option[User] = Option(user)

  def deleteUser(userId: Long): Option[Boolean] = Option(true)

}
