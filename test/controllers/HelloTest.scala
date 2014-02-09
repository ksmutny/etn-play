package controllers

import org.scalatest.FunSpec
import org.scalatest.Matchers
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.http.MimeTypes

class HelloTest extends FunSpec with Matchers {

  describe("Hello") {

    val response = Hello.world(FakeRequest())

    it("returns a text response") {
      status(response) should be(OK)
      contentType(response) should be(Some(MimeTypes.TEXT))
    }

    it("contains hello string") {
      contentAsString(response) should include("Hello")
    }

  }

}
