package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._

import models.Task

@Singleton
class GetTasksController @Inject()(components: ControllerComponents)
    extends AbstractController(components) with I18nSupport {

  def index: Action[AnyContent] = Action { implicit request =>
    val result = Task.findAll()
    Ok(views.html.index(result))
  }

}
