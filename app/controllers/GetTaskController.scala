package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._

import models.Task

@Singleton
class GetTaskController @Inject()(components: ControllerComponents)
    extends AbstractController(components) with I18nSupport {

  def index(contentId: Long): Action[AnyContent] = Action { implicit request =>
    val content = Task.findById(contentId).get
    Ok(views.html.show(content))
  }

}
