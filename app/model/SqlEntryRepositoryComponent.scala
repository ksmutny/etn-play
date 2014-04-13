package model

import scala.slick.lifted.Tag
import scala.slick.driver.MySQLDriver.simple._

trait SqlEntryRepositoryComponent extends EntryRepositoryComponent {

  val entryRepository = new SqlEntryRepository

  class SqlEntryRepository extends EntryRepository {
    def add(e: Entry): Entry = ???

    def findAll: Seq[Entry] = ???

    def findById(id: Long): Option[Entry] = ???

    def hate(id: Long): Int = ???

    def findHates(id: Long): Int = ???

  }

  class Entries(tag: Tag) extends Table[(Long, String, Long)](tag, "entry") {
    def id = column[Long]("id")
    def body = column[String]("body")
    def context = column[Long]("context")

    def * = (id, body, context)
  }

}