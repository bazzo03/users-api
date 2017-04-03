package co.s4n.infrastructure.database

import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.scalatest.time.{ Millis, Seconds, Span }

import scala.concurrent.duration._

trait CassandraSpec extends TestSuite {

  implicit val defaultPatientConfig =
    PatienceConfig(timeout = Span(15, Seconds), interval = Span(500, Millis))

  override def beforeAll(): Unit = {
    synchronized {
      EmbeddedCassandraServerHelper
        .startEmbeddedCassandra("embedded-cassandra.yaml", 120.seconds.toMillis)
      database.create(5.seconds)
      super.beforeAll()
    }
  }
}
