package co.s4n.infrastructure.connector

import com.outworkers.phantom.connectors.{ CassandraConnection, ContactPoint, ContactPoints }
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

/**
 * Created by seven4n on 13/02/17.
 */
object Connector {

  val host = "localhost"
  val keyspace: String = "users_keyspace"
  val port: Int = 9042

  lazy val connector_ = ContactPoint.local.noHeartbeat().keySpace(keyspace)

  lazy val connector: CassandraConnection = {
    // Con Heartbeat
    // ContactPoints(Seq(host)).keySpace(keyspace)

    ContactPoints(Seq(host)).noHeartbeat().keySpace(keyspace)

  }

  lazy val testConnector: CassandraConnection =
    ContactPoint.embedded.noHeartbeat().keySpace(keyspace)

}
