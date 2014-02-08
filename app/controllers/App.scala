package controllers

import play.api.mvc.{Action, Controller}

object App extends Controller {
  def index = Action(Ok("I am running"))
}
