package co.s4n.repository2

import co.s4n.entity.User

/**
 * Created by seven4n on 13/03/17.
 */
object UserDao {

  def saveUser(user: User): Option[Long] = Option(user.id)

  def findUser(id: Long): Option[User] = Option(User(1, "Daniel", "Bernal", "127.0.0.1"))

  def updateUser(user: User): Option[User] = Option(user)

  def deleteUser(userId: Long): Option[Boolean] = Option(true)

}
