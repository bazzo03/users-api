package co.s4n.database

import co.s4n.connector.Connector
import co.s4n.user.repository.Users
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef

/**
 * Created by seven4n on 13/02/17.
 */
class MyDatabase(override val connector: KeySpaceDef)
    extends Database[MyDatabase](connector) {

  object Users extends Users with connector.Connector
}

object MyDatabase extends MyDatabase(Connector.connector)

trait DataBaseProvider {
  def database: MyDatabase
}

trait UsersDatabase extends DataBaseProvider {
  override val database = MyDatabase
}
