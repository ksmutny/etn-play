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
import play.api.http.HeaderNames
import play.api.http.MimeTypes
import play.api.libs.json.JsObject
import play.api.libs.json.JsNumber
import play.api.libs.json.Json
import model.EntryWrites

class EntriesTest extends FunSpec with Matchers with EntryWrites {

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
      contentAsJson(c.listEntries(FakeRequest())) should be(Json.toJson(Seq(e1, e2)))
    }

    it("should return entry as plain text") {
      val c = new TestEntries
      val e1 = c.entryRepository.add(Entry("neco1"))
      val request = FakeRequest().withHeaders(HeaderNames.ACCEPT -> MimeTypes.TEXT)
      contentAsString(c.getEntry(e1.id.get)(request)) should be(e1.toString)

    }

    it("should return entry as JSON") {
      val c = new TestEntries
      val e1 = c.entryRepository.add(Entry("neco1"))
      val request = FakeRequest().withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)

      contentAsJson(c.getEntry(e1.id.get)(request)) should be(Json.toJson(e1))

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
        contentAsJson(result) should be(Json.arr(Entry(Some(id1), "neco1"), Entry(Some(id2), "neco2")))
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

    it("Should POST hate for entry") {
      new WithApplication {

        val response = route(FakeRequest(method = "POST", path = "/entries").withTextBody("neco1")).get
        val id = contentAsString(response).toLong
        val hateResponse = route(FakeRequest(method = "POST", path = s"/entries/$id/hate")).get
        contentAsString(hateResponse).toInt should be(1)
        status(hateResponse) should be(OK)

        val hatesResponse = route(FakeRequest(method = "GET", path = s"/entries/$id/hate")).get
        contentAsString(hatesResponse).toInt should be(1)
        status(hatesResponse) should be(OK)

      }
    }

  }
}