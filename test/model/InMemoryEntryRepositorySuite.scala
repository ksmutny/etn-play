package model

import org.scalatest.FunSpec
import org.scalatest.Matchers

class InMemoryEntryRepositorySuite extends FunSpec with Matchers {

  describe("Entry repository") {

    val repo = new InMemoryEntryRepositoryComponent().entryRepository

    it("should be empty when created") {

      repo.findAll should be('empty)

    }

    it("should contain all added entries in insertion order") {

      repo.add(Entry("a"))
      repo.add(Entry("r"))

      repo.findAll should be(Seq(Entry("a"), Entry("r")))

    }

  }

}