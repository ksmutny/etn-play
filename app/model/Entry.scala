package model

case class Entry(id: Option[Long], body: String)

object Entry {
  def apply(body: String): Entry = Entry(None, body)
}