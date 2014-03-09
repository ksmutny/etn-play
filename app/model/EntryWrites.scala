package model

import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import play.api.libs.json.JsNull
import play.api.libs.json.JsNumber

trait EntryWrites {

  implicit val entryWrites: Writes[Entry] = new Writes[Entry] {

    override def writes(e: Entry): JsValue = {
      Json.obj(
        "id" -> e.id,
        "body" -> e.body)
    }
  }

}