package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.mvc.AbstractController

import forms.TaskForm

trait TaskControllerSupport {
  this: AbstractController =>

  protected val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "content" -> nonEmptyText
    )(TaskForm.apply)(TaskForm.unapply)
  )

}
