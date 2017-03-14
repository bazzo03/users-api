package co.s4n.connector

import com.outworkers.phantom.connectors.{ CassandraConnection, ContactPoint, ContactPoints }
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

/**
 * Created by seven4n on 13/02/17.
 */
object Connector {
  private val config = ConfigFactory.load()

  //  private val hosts   = config.getStringList("localhost")
  //  private val keyspace = config.getString("keyspace")
  //  private val username = config.getString("")
  //  private val password = config.getString("")

  /**
   * Create a connector with the ability to connects to
   * multiple hosts in a secured cluster
   */
  lazy val connector: CassandraConnection = ContactPoint.local.keySpace("users_keyspace")

  /*  lazy val connector: CassandraConnection = ContactPoints(hosts)
    .withClusterBuilder(_.withCredentials(username, password))
    .keySpace(keyspace)*/
  /**
   * Create an embedded connector, testing purposes only
   */
  lazy val testConnector: CassandraConnection = ContactPoint.embedded.noHeartbeat().keySpace("users_keyspace")

}
