package controllers

import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession

import services.MessageService

@Singleton
class DeleteMessageController @Inject()(
    components: ControllerComponents, messageService: MessageService)
    extends AbstractController(components) with I18nSupport {

  def delete(messageId: Long): Action[AnyContent] = Action { implicit request =>
    implicit val session = AutoSession
    val result = messageService.deleteById(messageId)
    if (result > 0) {
      Redirect(routes.GetMessagesController.index())
    } else {
      InternalServerError(Messages("DeleteMessageError"))
    }
  }

}
