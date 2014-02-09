package model

trait EntryRepositoryComponent {

  def entryRepository: EntryRepository

  trait EntryRepository {

    def add(e: Entry): Entry

    def findAll: Seq[Entry]

  }
}