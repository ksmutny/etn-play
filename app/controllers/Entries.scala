package controllers

import play.api.mvc.Controller
import model.EntryRepositoryComponent
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Action
import model.Entry
import play.api.http.MimeTypes
import play.api.libs.json.Json
import model.EntryWrites
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation._

trait EntriesController extends EntryWrites {
  self: Controller with EntryRepositoryComponent =>

  val entryForm = Form(
    mapping(
      "body" -> (nonEmptyText verifying Constraints.maxLength(2048)),
      "context" -> longNumber)((b: String, c: Long) => Entry(None, b, c))((e: Entry) => Some(e.body, e.context)))

  def addEntry = Action { implicit request =>
    entryForm.bindFromRequest().fold(
      formWithErrors => BadRequest,
      entry =>
        {
          val e = entryRepository.add(entry)
          Ok(e.id.get.toString)
        })
    //    request.body.asText match {
    //      case None | Some("") => BadRequest
    //      case Some(body) =>
    //        val e = entryRepository.add(Entry(body, 0))
    //        Ok(e.id.get.toString)
    //    }
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

  def hateEntry(id: Long) = Action {
    Ok(entryRepository.hate(id).toString)
  }

  def getHates(id: Long) = Action {
    Ok(entryRepository.findHates(id).toString)
  }
}

object Entries extends Controller
  with EntriesController
  with InMemoryEntryRepositoryComponent
