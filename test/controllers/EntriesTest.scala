package controllers

import org.scalatest.Matchers
import org.scalatest.FunSpec
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Controller
import play.api.test.Helpers._
import play.api.test.FakeRequest
import model.Entry
import play.api.libs.json.JsString

class EntriesTest extends FunSpec with Matchers {

  class TestEntries extends Controller
    with EntriesController
    with InMemoryEntryRepositoryComponent

  describe("Entries controller") {

    it("should contain posted entry") {
      val c = new TestEntries
      status(c.addEntry(FakeRequest().withTextBody("neco"))) should be(OK)
      c.entryRepository.findAll should be(Seq(Entry("neco")))
    }

    it("should not accept empty entry") {
      val c = new TestEntries
      status(c.addEntry(FakeRequest().withTextBody(""))) should be(BAD_REQUEST)
      c.entryRepository.findAll should be(Seq())
    }

    it("should not accept request with no body") {
      val c = new TestEntries
      status(c.addEntry(FakeRequest())) should be(BAD_REQUEST)
      c.entryRepository.findAll should be(Seq())
    }

    it("should not accept request with json body") {
      val c = new TestEntries
      status(c.addEntry(FakeRequest().withJsonBody(JsString("neco")))) should be(BAD_REQUEST)
      c.entryRepository.findAll should be(Seq())
    }
  }
}