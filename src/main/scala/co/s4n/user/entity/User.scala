package co.s4n.user.entity

sealed trait UserServiceProtocol
case class UserNotFound(m: String) extends UserServiceProtocol
case class UserFound(u: User) extends UserServiceProtocol
case class DatabaseConnectionFailed(m: String) extends UserServiceProtocol
// ...
case class User(id: Long, name: String, lastname: String, ip: String) {
  override def toString: String = "id: " + id + " -name: " + name + " -lastname: " + lastname
}

