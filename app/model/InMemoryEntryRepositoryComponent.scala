package model

class InMemoryEntryRepositoryComponent extends EntryRepositoryComponent {

  val entryRepository = new InMemoryEntryRepository

  class InMemoryEntryRepository extends EntryRepository {

    private var entries = List.empty[Entry]

    def add(e: Entry): Unit = entries = e :: entries

    def findAll(): Seq[Entry] = entries.reverse

  }

}

