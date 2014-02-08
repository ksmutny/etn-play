package controllers

import models.InMemoryEntryRepositoryComponent
import play.api.mvc.{Action, Controller}

trait Entries {
  this: Controller with InMemoryEntryRepositoryComponent =>

  def newEntry = Action { request =>
    request.body.asText match {
      case Some("") | None => BadRequest
      case Some(entryBody) =>
        entryRepository.add(entryBody)
        Ok
    }
  }
}
