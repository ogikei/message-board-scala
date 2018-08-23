package controllers

import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc._

import forms.TaskForm
import models.Task

@Singleton
class UpdateTaskController @Inject()(components: ControllerComponents)
    extends AbstractController(components)
        with I18nSupport
        with TaskControllerSupport {

  def index(messageId: Long): Action[AnyContent] = Action { implicit request =>
    val result = Task.findById(messageId).get
    val filledForm = form.fill(TaskForm(result.id, result.status.get, result.content)
    )
    Ok(views.html.edit(filledForm))
  }

  // 追加
  def update: Action[AnyContent] = Action { implicit request =>
    form
        .bindFromRequest()
        .fold(
          formWithErrors => BadRequest(views.html.edit(formWithErrors)), { model =>
            implicit val session = AutoSession
            val result = Task
                .updateById(model.id.get)
                .withAttributes(
                  'status -> model.status,
                  'content -> model.content
                )
            if (result > 0)
              Redirect(routes.GetTasksController.index())
            else
              InternalServerError(Messages("UpdateTaskError"))
          }
        )
  }

}
