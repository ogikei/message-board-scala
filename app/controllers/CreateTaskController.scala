package controllers

import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession

import models.Task

@Singleton
class CreateTaskController @Inject()(components: ControllerComponents)
    extends AbstractController(components)
        with I18nSupport
        with TaskControllerSupport {

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.create(form))
  }

  // 追加
  def create: Action[AnyContent] = Action { implicit request =>
    form
        .bindFromRequest()
        .fold(
          formWithErrors => BadRequest(views.html.create(formWithErrors)), { model =>
            implicit val session = AutoSession
            val message = Task(None, model.content)
            val result = Task.create(message)
            if (result > 0) {
              Redirect(routes.GetTasksController.index())
            } else {
              InternalServerError(Messages("CreateTaskError"))
            }
          }
        )
  }
}
