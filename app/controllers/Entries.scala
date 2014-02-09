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
        val e = entryRepository.add(Entry(body))
        Ok(e.id.get.toString)
    }
  }

  def listEntries = Action {
    Ok(entryRepository.findAll mkString "\n")
  }

  def getEntry(id: Long) = Action {
    entryRepository.findById(id) match {
      case Some(e) => Ok(e.toString())
      case None => NotFound
    }

  }
}

object Entries extends Controller
  with EntriesController
  with InMemoryEntryRepositoryComponent
