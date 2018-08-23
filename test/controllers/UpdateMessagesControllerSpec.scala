package controllers

import org.scalatest._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.test._
import play.api.test.Helpers._
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import scalikejdbc.PlayModule

import services.{MessageService, MockMessageService}

class UpdateMessagesControllerSpec extends FunSpec with MustMatchers with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
        .disable[PlayModule]
        .overrides(bind[MessageService].to[MockMessageService])
        .build()

  describe("GetMessageController") {
    describe("route of GetMessageController#index") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(GET, routes.UpdateMessageController.index(1).toString)).get
        status(result) mustBe OK
      }
    }
    describe("route of GetMessageController#create") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(POST, routes.UpdateMessageController.update().toString)
              .withFormUrlEncodedBody("id" -> "1", "title" -> "a", "body" -> "b")).get
        status(result) mustBe SEE_OTHER
      }
    }
  }

}
