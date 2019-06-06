package controllers

import java.time.Instant
import java.util.UUID

import javax.inject._
import models._
import play.api.mvc._
import play.twirl.api.XmlFormat
import play.api.Logger

import scala.util.Random

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  var events = Vector.empty[(String, String)]

  val logger = Logger("play")

  private def renderVast(
                          creative: Creative,
                          sessionId: String
                        ): XmlFormat.Appendable = {
    views.xml.vast.render(creative.id, creative.url, sessionId)
  }

  def index() = Action { request =>
    logger.info(request.uri)
    Ok(views.html.demo.render())
  }

  def getAd() = Action { request =>
    logger.info(request.uri)

    Ok(renderVast(Random.shuffle(Creative.all).head, sessionId = "0000"))
  }


  def track() = Action { request =>
    logger.info(request.uri)
    logger.info(request.queryString("action").head)

    Ok
  }

}
