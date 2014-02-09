package model

trait EntryRepositoryComponent {

  def entryRepository: EntryRepository

  trait EntryRepository {

    def add(e: Entry): Unit

    def findAll: Seq[Entry]

  }
}