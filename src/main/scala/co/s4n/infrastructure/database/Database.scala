package co.s4n.infrastructure.database

import co.s4n.infrastructure.connector.Connector
import co.s4n.user.dao.UsersDao
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef

/**
 * Created by seven4n on 13/02/17.
 */
class MyDatabase(override val connector: KeySpaceDef)
    extends Database[MyDatabase](connector) {

  object Users extends UsersDao with connector.Connector

}

object MyDatabase extends MyDatabase(Connector.connector)

trait DataBaseProvider {
  def database: MyDatabase
}

trait UsersDatabase extends DataBaseProvider {
  override val database = MyDatabase
}
