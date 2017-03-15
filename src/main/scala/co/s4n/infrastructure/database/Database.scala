package co.s4n.infrastructure.database

import co.s4n.user.dao.UsersDao
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.connectors
import com.outworkers.phantom.connectors.CassandraConnection

/**
 * Created by seven4n on 13/02/17.
 */
class UserDataBase(override val connector: CassandraConnection)
    extends Database[UserDataBase](connector) {

  object Users extends UsersDao with Connector

}

object UserDataBase extends UserDataBase(
  connectors.ContactPoint.local.noHeartbeat().keySpace("users_keyspace")
)
