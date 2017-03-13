package co.s4n.entity

/**
 * Created by seven4n on 13/03/17.
 */
case class User(id: Long, name: String, lastname: String, ip: String) {
  override def toString: String = "id: " + id + " -name: " + name + " -lastname: " + lastname
}
