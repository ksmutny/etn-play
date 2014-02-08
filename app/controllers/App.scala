package controllers

import play.api.mvc.{Action, Controller}

trait App {
  this: Controller =>

  def index = Action(Ok("Hello, I am running"))
}

object App extends Controller with App