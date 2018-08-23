package controllers

import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession

import models.Task

@Singleton
class DeleteTaskController @Inject()(components: ControllerComponents)
    extends AbstractController(components) with I18nSupport {

  def delete(messageId: Long): Action[AnyContent] = Action { implicit request =>
    implicit val session = AutoSession
    val result = Task.deleteById(messageId)
    if (result > 0) {
      Redirect(routes.GetTasksController.index())
    } else {
      InternalServerError(Messages("DeleteTaskError"))
    }
  }

}
