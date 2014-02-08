package models

trait EntryRepositoryComponent {
  def entryRepository: EntryRepository

  trait EntryRepository {
    def add(body: String)
    def list: List[Entry]
  }
}
