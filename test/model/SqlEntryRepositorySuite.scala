package model

import org.scalatest.FunSpec
import org.scalatest.Matchers
import slick.jdbc.{ GetResult, StaticQuery }
import StaticQuery.interpolation
import scala.slick.driver.MySQLDriver.simple._
import play.api.test.Helpers._
import play.api.test.FakeApplication

class SqlEntryRepositorySuite extends FunSpec with Matchers {

  describe("sql test") {

    it("should insert entry to DB") {
      running(FakeApplication()) {
        val comp = new SqlEntryRepositoryComponent {}
        val e = Entry(None, "body", 0)
        val inserted = comp.entryRepository.add(e)
        inserted.body should be(e.body)
        inserted.context should be(e.context)
        inserted.id should not be 'empty

        val query = sql"select id, body, context from entry where id = ${inserted.id.get}"
        comp.database.withSession { implicit session =>

          // implicit val getEntryResult = GetResult[(Long, String, Long)](r => (r.<<, r.<<, r.<<))
          implicit val getEntryResult = GetResult(r => (r.nextLong, r.nextString, r.nextLong))
          val (id, body, context) = query.as[(Long, String, Long)].first
          Entry(Some(id), body, context) should be(inserted)
        }
      }
    }

    it("should find inserted entry") {
      running(FakeApplication()) {
        val comp = new SqlEntryRepositoryComponent {}
        val e = Entry(None, "body", 0)
        val inserted = comp.entryRepository.add(e)
        val stored = comp.entryRepository.findById(inserted.id.get)
        stored.get should be(inserted)
      }
    }

  }

}