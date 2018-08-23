package controllers

import java.time.ZonedDateTime
import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc._
import jsr310._

import forms.MessageForm
import models.Message
import services.MessageService // 手動でインポートしてください。

@Singleton
class UpdateMessageController @Inject()(
    components: ControllerComponents, messageService: MessageService)
    extends AbstractController(components)
        with I18nSupport
        with MessageControllerSupport {

  def index(messageId: Long): Action[AnyContent] = Action { implicit request =>
    val result = messageService.findById(messageId).get
    val filledForm = form.fill(MessageForm(result.id, result.title.getOrElse(""), result.body))
    Ok(views.html.edit(filledForm))
  }

  // 追加
  def update: Action[AnyContent] = Action { implicit request =>
    form
        .bindFromRequest()
        .fold(
          formWithErrors => BadRequest(views.html.edit(formWithErrors)), { model =>
            implicit val session = AutoSession
            val result = messageService.update(model.id.get, model.title, model.body)
            if (result > 0)
              Redirect(routes.GetMessagesController.index())
            else
              InternalServerError(Messages("UpdateMessageError"))
          }
        )
  }

}
