package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._

import services.MessageService

@Singleton
class GetMessageController @Inject()(
    components: ControllerComponents, messageService: MessageService)
    extends AbstractController(components) with I18nSupport {

  def index(messageId: Long): Action[AnyContent] = Action { implicit request =>
    val message = messageService.findById(messageId).get
    Ok(views.html.show(message))
  }

}
