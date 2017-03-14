package co.s4n.user.dao

import co.s4n.user.entity.User
import com.datastax.driver.core.Row
import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ ConsistencyLevel, ResultSet, StringColumn, _ }

import scala.concurrent.Future

/**
 * Created by seven4n on 13/02/17.
 */
abstract class UsersDao extends CassandraTable[UsersDao, User] with RootConnector {

  override lazy val tableName: String = "users"

  object Id extends LongColumn(this) with PartitionKey

  object Name extends StringColumn(this)

  object Lastname extends StringColumn(this)

  object Ip extends StringColumn(this)

  override def fromRow(r: Row): User = User(Id(r), Name(r), Lastname(r), Ip(r))

  def findUser(id: Long): Future[Option[User]] = {
    select
      .where(_.Id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def saveUser(user: User): Future[ResultSet] = {
    insert
      .value(_.Id, user.id)
      .value(_.Name, user.name)
      .value(_.Lastname, user.lastname)
      .value(_.Ip, user.ip)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def deleteUser(userId: Long): Future[ResultSet] = {
    delete
      .where(_.Id eqs userId)
      .future()
  }

  def findAllUsers(): Future[List[User]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .fetch()
  }
}
