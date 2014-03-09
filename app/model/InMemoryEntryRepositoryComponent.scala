package model

trait InMemoryEntryRepositoryComponent extends EntryRepositoryComponent {

  val entryRepository = new InMemoryEntryRepository

  class InMemoryEntryRepository extends EntryRepository {

    private var id = 1l
    private var entries = List.empty[Entry]
    private var hates = Map.empty[Long, Int]

    def add(e: Entry): Entry = {
      val en = e.copy(id = Some(id))
      entries = en :: entries
      hates = hates.updated(id, 0)
      id += 1
      en
    }

    def findAll(): Seq[Entry] = entries.reverse

    def findById(id: Long): Option[Entry] = entries.find(_.id == Some(id))

    def hate(id: Long): Int = {
      //FIXME Mka
      val ret = hates(id) + 1 // for SMi
      //      val ret = hates.apply(id) + 1 // for PSm
      hates = hates.updated(id, ret)
      ret
    }

    def findHates(id: Long): Int = {
      hates(id)
    }

  }

}

