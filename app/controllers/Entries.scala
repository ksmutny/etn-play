package controllers

import play.api.mvc.Controller
import model.EntryRepositoryComponent
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Action
import model.Entry
import play.api.http.MimeTypes
import play.api.libs.json.Json
import model.EntryWrites

trait EntriesController extends EntryWrites {
  self: Controller with EntryRepositoryComponent =>

  def addEntry = Action { request =>
    request.body.asText match {
      case None | Some("") => BadRequest
      case Some(body) =>
        val e = entryRepository.add(Entry(body))
        Ok(e.id.get.toString)
    }
  }

  def listEntries = Action {
    Ok(Json.toJson(entryRepository.findAll))
  }

  def getEntry(id: Long) = Action { implicit request =>

    entryRepository.findById(id) match {
      case Some(e) =>
        if (request.accepts(MimeTypes.TEXT)) {
          Ok(e.toString())
        } else if (request.accepts(MimeTypes.JSON)) {
          Ok(Json.toJson(e))

          //          Ok(Json.obj(
          //            "id" -> e.id.get,
          //            "body" -> e.body))
        } else {
          BadRequest
        }

      case None => NotFound

    }
  }
}

object Entries extends Controller
  with EntriesController
  with InMemoryEntryRepositoryComponent
