package model

trait EntryRepositoryComponent {

  def entryRepository: EntryRepository

  trait EntryRepository {

    def add(e: Entry): Entry

    def findAll: Seq[Entry]

    def findById(id: Long): Option[Entry]

    def hate(id: Long): Int

    def findHates(id: Long): Int
  }
}