package model

import org.scalatest.FunSpec
import org.scalatest.Matchers

class InMemoryEntryRepositorySuite extends FunSpec with Matchers {

  describe("Entry repository") {

    val repo = new InMemoryEntryRepositoryComponent {}.entryRepository

    it("should be empty when created") {

      repo.findAll should be('empty)

    }

    it("should contain all added entries in insertion order") {

      val e1 = repo.add(Entry(None, "a"))
      val e2 = repo.add(Entry(None, "r"))

      e1.id should not be None
      e2.id should not be None
      e1.id should not be e2.id
      repo.findAll should be(Seq(e1, e2))

    }

    it("should find entry by id") {
      val e1 = repo.add(Entry(None, "karel"))
      val e2 = repo.add(Entry(None, "zdenek"))

      repo.findById(e1.id.get) should be(Some(e1))
      repo.findById(e2.id.get) should be(Some(e2))
      repo.findById(-1000) should be(None)
    }

  }

}
