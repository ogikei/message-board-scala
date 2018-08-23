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

class GetMessageControllerSpec extends FunSpec with MustMatchers with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
        .disable[PlayModule]
        .overrides(bind[MessageService].to[MockMessageService])
        .build()

  describe("GetMessageController") {
    describe("route of GetMessageController#index") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(GET, routes.GetMessageController.index(1).toString)).get
        status(result) mustBe OK
      }
    }
  }

}
