package co.s4n.infrastructure.database

import co.s4n.user.dao.UsersDao
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.connectors
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.DatabaseProvider

/**
 * Created by seven4n on 13/02/17.
 */
class UserDataBase(override val connector: CassandraConnection)
    extends Database[UserDataBase](connector) {

  object Users extends UsersDao with Connector

}

// Production Database

object UserDataBase extends UserDataBase(
  connectors.ContactPoint.local.noHeartbeat().keySpace("users_keyspace")
)

trait UserDbProvider extends DatabaseProvider[UserDataBase] {
  override def database: UserDataBase = UserDataBase
}

// Testing Database

object UserTestDataBase extends UserDataBase(
  connectors.ContactPoint.embedded.noHeartbeat().keySpace("users_keyspace")
)

trait UserTestDbProvider extends DatabaseProvider[UserDataBase] {
  override def database: UserDataBase = UserTestDataBase
}