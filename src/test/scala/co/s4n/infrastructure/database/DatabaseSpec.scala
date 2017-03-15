package co.s4n.infrastructure.database
import co.s4n.user.entity.User

/**
 * Created by seven4n on 15/03/17.
 */
class DatabaseSpec extends TestSuite {

  it should "Create a new User and query it - DB" in {
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

  it should "Create a new User" in {
    val user = User(111, "UserName", "Lastname", "127.0.0.1")

    val x = for {
      saved <- database.Users.saveUser(user)
    } yield saved

    whenReady(x) { save =>
      save.isExhausted
      database.Users.deleteUser(user.id)
    }
  }

  it should "Update a user" in {
    val user = User(111, "UserName", "Lastname", "127.0.0.1")
    val user2 = User(1111, "UserName1", "Lastname1", "127.0.0.1")

    val x = for {
      saved <- database.Users.saveUser(user)
      found <- database.Users.findUser(user.id)
      updated <- database.Users.saveUser(user)
      found1 <- database.Users.findUser(user.id)
    } yield found1

    whenReady(x) { res =>
      res shouldBe defined
      database.Users.deleteUser(user.id)
    }
  }

  it should "Delete a user" in {
    val user = User(111, "UserName", "Lastname", "127.0.0.1")

    val chain = for {
      saved <- database.Users.saveUser(user)
      found <- database.Users.findUser(user.id)
      deleted <- database.Users.deleteUser(user.id)
      found <- database.Users.findUser(user.id)
    } yield found

    whenReady(chain) { res2 =>
      res2 shouldBe empty
      database.Users.deleteUser(user.id)
    }
  }

}