package model

trait InMemoryEntryRepositoryComponent extends EntryRepositoryComponent {

  val entryRepository = new InMemoryEntryRepository

  class InMemoryEntryRepository extends EntryRepository {

    private var id = 1l
    private var entries = List.empty[Entry]

    def add(e: Entry): Entry = {
      val en = e.copy(id = Some(id))
      id += 1
      entries = en :: entries
      en
    }

    def findAll(): Seq[Entry] = entries.reverse

  }

}

