package model

import org.scalatest.Matchers
import org.scalatest.FunSpec
import play.api.libs.json.Json
import play.api.libs.json.JsNull

class EntryWritesSuite extends FunSpec with Matchers with EntryWrites {

  describe("Entry writes") {

    it("works with no id") {

      val json = Json.toJson(Entry(None, "entry", 10))
      val exp = Json.obj("id" -> JsNull, "body" -> "entry", "context" -> 10)

      json should be(exp)

    }
    it("works") {

      val json = Json.toJson(Entry(Some(5l), "entry", 11))
      val exp = Json.obj("id" -> 5l, "body" -> "entry", "context" -> 11)

      json should be(exp)

    }

  }

}