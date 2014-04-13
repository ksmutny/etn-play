package model

import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import play.api.libs.json.JsNull
import play.api.libs.json.JsNumber
import play.api.libs.functional.syntax._
import play.api.libs.json.__ //?!?

trait EntryWrites {

  implicit val entryWrites: Writes[Entry] = (
    (__ \ "id").format[Option[Long]] and
    (__ \ "body").format[String] and
    (__ \ "context").format[Long])(Entry.apply, unlift(Entry.unapply))

  // alternative non-DSL solution  
  //  implicit val entryWrites: Writes[Entry] = new Writes[Entry] {
  //
  //    override def writes(e: Entry): JsValue = {
  //      Json.obj(
  //        "id" -> e.id,
  //        "body" -> e.body)
  //    }
  //  }

}