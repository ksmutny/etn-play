package controllers

import play.api.mvc.Controller
import model.EntryRepositoryComponent
import model.InMemoryEntryRepositoryComponent
import play.api.mvc.Action

trait EntriesController {
  self: Controller with EntryRepositoryComponent =>

  def addEntry = Action {
    NotImplemented
  }
}

object Entries extends Controller
  with EntriesController
  with InMemoryEntryRepositoryComponent
