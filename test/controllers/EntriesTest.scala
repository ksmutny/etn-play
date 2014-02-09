package controllers

import org.scalatest.Matchers
import org.scalatest.FunSpec
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Controller
import play.api.test.Helpers._
import play.api.test.FakeRequest

class EntriesTest extends FunSpec with Matchers {

  class TestEntries extends Controller
    with EntriesController
    with InMemoryEntryRepositoryComponent

  describe("Entries controller") {
    val c = new TestEntries

    it("should contain posted entry") {
      status(c.addEntry(FakeRequest().withTextBody("neco"))) should be(OK)
    }
  }
}