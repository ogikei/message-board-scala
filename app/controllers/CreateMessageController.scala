package controllers

import java.time.ZonedDateTime
import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession

import models.Message
import services.MessageService

@Singleton
class CreateMessageController @Inject()(
    components: ControllerComponents, messageService: MessageService)
    extends AbstractController(components)
        with I18nSupport
        with MessageControllerSupport {

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
            val now = ZonedDateTime.now()
            val message = Message(None, Some(model.title), model.body, now, now)
            val result = messageService.create(message)
            if (result > 0) {
              Redirect(routes.GetMessagesController.index())
            } else {
              InternalServerError(Messages("CreateMessageError"))
            }
          }
        )
  }
}
