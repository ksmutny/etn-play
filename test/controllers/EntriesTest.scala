package controllers

import org.scalatest.Matchers
import org.scalatest.FunSpec
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Controller
import play.api.test.Helpers._
import play.api.test.FakeRequest
import model.Entry
import play.api.libs.json.JsString
import play.api.test.WithApplication

class EntriesTest extends FunSpec with Matchers {

  class TestEntries extends Controller
    with EntriesController
    with InMemoryEntryRepositoryComponent

  describe("Entries controller") {

    it("should contain posted entry") {
      val c = new TestEntries
      val res = c.addEntry(FakeRequest().withTextBody("neco"))
      status(res) should be(OK)
      val id = contentAsString(res).toLong
      c.entryRepository.findAll should be(Seq(Entry(Some(id), "neco")))
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

    it("should list entries") {
      val c = new TestEntries
      val e1 = c.entryRepository.add(Entry("neco1"))
      val e2 = c.entryRepository.add(Entry("neco2"))
      contentAsString(c.listEntries(FakeRequest())) should be(s"$e1\n$e2")
    }
  }

  describe("URL Entries") {
    it("POST should return OK and list entries") {
      new WithApplication {
        val response1 = route(FakeRequest(method = "POST", path = "/entries").withTextBody("neco1")).get
        status(response1) should be(OK)
        val id1 = contentAsString(response1).toLong
        val response2 = route(FakeRequest(method = "POST", path = "/entries").withTextBody("neco2")).get
        val id2 = contentAsString(response2).toLong
        status(response2) should be(OK)
        val result = route(FakeRequest(method = "GET", path = "/entries")).get
        contentAsString(result) should be(s"Entry(Some($id1),neco1)\nEntry(Some($id2),neco2)")
      }
    }

    it("Should return entry by id") {
      new WithApplication {
        val response1 = route(FakeRequest(method = "POST", path = "/entries").withTextBody("neco1")).get
        val id1 = contentAsString(response1).toLong
        val result = route(FakeRequest(method = "GET", path = "/entries/" + id1)).get
        contentAsString(result) should be(s"Entry(Some($id1),neco1)")
        status(result) should be(OK)
      }
    }

  }
}