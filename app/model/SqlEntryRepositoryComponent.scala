package model

import scala.slick.lifted.Tag
import scala.slick.driver.MySQLDriver.simple._
import play.api.db.DB
import play.api.Play.current

trait SqlEntryRepositoryComponent extends EntryRepositoryComponent {

  val entryRepository = new SqlEntryRepository
  val database = Database.forDataSource(DB.getDataSource())

  class SqlEntryRepository extends EntryRepository {

    val entries = TableQuery[Entries]

    def add(e: Entry): Entry = database.withSession { implicit session =>
      val insertEntries = entries returning (entries.map(_.id))
      val insertedId = insertEntries.insert((0L, e.body, e.context)) // insert ignoruje hodnotu autoincerment sloupcu
      Entry(Some(insertedId), e.body, e.context)
    }

    def findAll: Seq[Entry] = ???

    def findById(id: Long): Option[Entry] = ???

    def hate(id: Long): Int = ???

    def findHates(id: Long): Int = ???

  }

  class Entries(tag: Tag) extends Table[(Long, String, Long)](tag, "entry") {
    def id = column[Long]("id", O.AutoInc) // O je columnOptions
    def body = column[String]("body")
    def context = column[Long]("context")

    def * = (id, body, context)
  }

}