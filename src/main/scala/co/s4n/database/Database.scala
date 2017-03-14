package co.s4n.database

import co.s4n.connector.Connector
import co.s4n.user.repository.ConcreteUsers
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database

/**
 * Created by seven4n on 13/02/17.
 */
class MyDatabase(override val connector: CassandraConnection)
    extends Database[MyDatabase](connector) {

  object Users extends ConcreteUsers with connector.Connector
}

object MyDatabase extends MyDatabase(Connector.connector)

trait DataBaseProvider {
  def database: MyDatabase
}

trait UsersDatabase extends DataBaseProvider {
  override val database = MyDatabase
}
