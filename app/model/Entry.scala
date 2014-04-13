package model

case class Entry(id: Option[Long], body: String, context: Long)

object Entry {
  def apply(body: String, context: Long): Entry = Entry(None, body, context)
}