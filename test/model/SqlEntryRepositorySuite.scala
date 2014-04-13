package model

import org.scalatest.FunSpec
import org.scalatest.Matchers
import slick.jdbc.{ GetResult, StaticQuery }
import StaticQuery.interpolation
import play.api.db.DB
import scala.slick.driver.MySQLDriver.simple._
import play.api.test.Helpers._
import play.api.test.FakeApplication
import play.api.Play.current

class SqlEntryRepositorySuite extends FunSpec with Matchers {

  def repository = new SqlEntryRepositoryComponent {}.entryRepository

  describe("sql test") {

    it("should insert entry to DB") {
      running(FakeApplication()) {
        val e = Entry("body", 0)
        val inserted = repository.add(e)
        inserted.body should be(e.body)
        inserted.context should be(e.context)
        inserted.id should not be 'empty

        val database = Database.forDataSource(DB.getDataSource())

        val query = sql"select id, body, context from entry where id = ${inserted.id.get}"
        database.withSession { implicit session =>

          // implicit val getEntryResult = GetResult[(Long, String, Long)](r => (r.<<, r.<<, r.<<))
          implicit val getEntryResult = GetResult(r => (r.nextLong, r.nextString, r.nextLong))
          val (id, body, context) = query.as[(Long, String, Long)].first
          Entry(Some(id), body, context) should be(inserted)
        }
      }
    }

  }

}