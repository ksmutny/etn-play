package models

trait InMemoryEntryRepositoryComponent extends EntryRepositoryComponent {
  val entryRepository = new InMemoryEntryRepository

  class InMemoryEntryRepository extends EntryRepository {
    private var entries = List[Entry]()

    def add(body: String) = entries = entries :+ Entry(body)
    def list = entries
  }
}
