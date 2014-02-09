package controllers

import play.api.mvc.Controller
import play.api.mvc.Action

object Hello extends Controller {

  def world = Action(Ok("Hello world"))

}