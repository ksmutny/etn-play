package model

import org.scalatest.Matchers
import org.scalatest.FunSpec
import play.api.libs.json.Json
import play.api.libs.json.JsNull

class EntryWritesSuite extends FunSpec with Matchers with EntryWrites {

  describe("Entry writes") {

    it("works with no id") {

      val json = Json.toJson(Entry("entry"))
      val exp = Json.obj("id" -> JsNull, "body" -> "entry")

      json should be(exp)

    }
    it("works") {

      val json = Json.toJson(Entry(Some(5l), "entry"))
      val exp = Json.obj("id" -> 5l, "body" -> "entry")

      json should be(exp)

    }

  }

}