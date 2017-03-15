package co.s4n.infrastructure.database

import com.outworkers.phantom.dsl.DatabaseProvider
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

/**
 * Created by seven4n on 15/03/17.
 */
trait UsersDbProvider extends DatabaseProvider[UserDataBase] {
  override def database: UserDataBase = UserDataBase
}

abstract class TestSpec
  extends FlatSpec
  with Matchers
  with ScalaFutures

trait TestSuite extends TestSpec
    with UsersDbProvider with UserDataBase.Connector {

  implicit val ec = scala.concurrent.ExecutionContext.global
}