package controllers

import scala.concurrent.Future
import play.api.mvc.{SimpleResult, Controller}

import org.scalatest.{Matchers, FunSpec}
import play.api.test._
import play.api.test.Helpers._

class AppTest extends FunSpec with Matchers {

  class TestApp extends Controller with App

  describe("GET /") {
    val app = new TestApp
    val response: Future[SimpleResult] = app.index()(FakeRequest())

    it ("should return a text response") {
      status(response) should be (OK)
      contentType(response) should be (Some("text/plain"))
    }

    it ("should say hello") {
      contentAsString(response) should include ("Hello")
    }
  }
}
