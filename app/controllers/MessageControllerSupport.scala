package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.mvc.AbstractController

import forms.MessageForm

trait MessageControllerSupport {
  this: AbstractController =>

  protected val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "title" -> nonEmptyText,
      "body" -> nonEmptyText
    )(MessageForm.apply)(MessageForm.unapply)
  )

}
