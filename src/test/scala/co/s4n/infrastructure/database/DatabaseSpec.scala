package co.s4n.infrastructure.database
import co.s4n.user.entity.User

/**
 * Created by seven4n on 15/03/17.
 */
class DBTest extends TestSuite {

  it should "Create a new User - DB" in {
    val user = User(111, "UserName", "Lastname", "127.0.0.1")

    val x = for {
      saved <- database.Users.saveUser(user)
      res <- database.Users.findUser(user.id)
    } yield res

    whenReady(x) { res =>
      res shouldBe defined
      database.Users.deleteUser(user.id)
    }
  }

}