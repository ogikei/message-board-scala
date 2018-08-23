package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._

import services.MessageService

@Singleton
class GetMessagesController @Inject()(components: ControllerComponents, messageService: MessageService)
    extends AbstractController(components) with I18nSupport {

  def index: Action[AnyContent] = Action { implicit request =>
    val result = messageService.findAll()
    Ok(views.html.index(result))
  }

}
