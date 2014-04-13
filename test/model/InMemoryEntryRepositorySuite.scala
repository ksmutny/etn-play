package model

import org.scalatest.FunSpec
import org.scalatest.Matchers

class InMemoryEntryRepositorySuite extends FunSpec with Matchers {

  describe("Entry repository") {

    def newRepo = new InMemoryEntryRepositoryComponent {}.entryRepository

    it("should be empty when created") {
      val repo = newRepo
      repo.findAll should be('empty)
    }

    it("should contain all added entries in insertion order") {
      val repo = newRepo
      val e1 = repo.add(Entry(None, "a", 3))
      val e2 = repo.add(Entry(None, "r", 4))

      e1.id should not be None
      e2.id should not be None
      e1.id should not be e2.id
      repo.findAll should be(Seq(e1, e2))

    }

    it("should find entry by id") {
      val repo = newRepo
      val e1 = repo.add(Entry(None, "karel", 5))
      val e2 = repo.add(Entry(None, "zdenek", 6))

      repo.findById(e1.id.get) should be(Some(e1))
      repo.findById(e2.id.get) should be(Some(e2))
      repo.findById(-1000) should be(None)
    }

    it("should throw exception if hating nonexistent entry") {
      val repo = newRepo
      evaluating { repo.hate(-139) } should produce[NoSuchElementException]
    }

    it("should add 1 hate and return 1") {
      val repo = newRepo
      val e2 = repo.add(Entry(None, "zdenek neni blbec", 7))
      repo.hate(e2.id.get) should be(1)
      repo.hate(e2.id.get) should be(2)
    }

    it("should set 0 hates for new entry") {
      val repo = newRepo
      val e1 = repo.add(Entry(None, "zdenek mozna neni blbec", 8))
      repo.findHates(e1.id.get) should be(0)
    }

    it("should increment and count hates for new entry") {
      val r = newRepo
      val e1 = r.add(Entry(None, "zdenek mozna neni blbec", 9))
      r.findHates(e1.id.get) should be(0)
      r.hate(e1.id.get) should be(1)
      r.findHates(e1.id.get) should be(1)
    }
  }
}
