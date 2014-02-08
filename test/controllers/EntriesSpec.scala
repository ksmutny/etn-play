package controllers

import play.api.mvc.Controller
import models.{Entry, InMemoryEntryRepositoryComponent}

import org.scalatest.{FunSpec, Matchers}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class EntriesSpec extends FunSpec with Matchers {

  class TestEntries extends Controller with Entries with InMemoryEntryRepositoryComponent

  describe("Entries") {
    it("should put an entry into repository and return OK") {
      val entries = new TestEntries
      val response = entries.newEntry()(FakeRequest().withTextBody("brand new entry"))

      entries.entryRepository.list should contain (Entry("brand new entry"))
      status(response) should be (OK)
    }

    it("should return BAD REQUEST for an empty entry") {
      val entries = new TestEntries
      val response = entries.newEntry()(FakeRequest().withTextBody(""))

      entries.entryRepository.list should be ('empty)
      status(response) should be (BAD_REQUEST)
    }

    it("should return BAD REQUEST for no body") {
      val entries = new TestEntries
      val response = entries.newEntry()(FakeRequest())

      entries.entryRepository.list should be ('empty)
      status(response) should be (BAD_REQUEST)
    }
  }

}
