package controllers

import play.api.mvc.Controller
import model.EntryRepositoryComponent
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Action
import model.Entry

trait EntriesController {
  self: Controller with EntryRepositoryComponent =>

  def addEntry = Action { request =>
    request.body.asText match {
      case None | Some("") => BadRequest
      case Some(body) =>
        entryRepository.add(Entry(body))
        Ok
    }
  }

  def listEntries = Action {
    Ok(entryRepository.findAll mkString "\n")
  }
}

object Entries extends Controller
  with EntriesController
  with InMemoryEntryRepositoryComponent
