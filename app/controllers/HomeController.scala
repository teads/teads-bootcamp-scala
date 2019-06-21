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

  var events = Vector.empty[TrackingEvent]

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

    val allCreatives = Random.shuffle(Creative.all)

    val eligibleCreatives = allCreatives.filterNot(creative => creative.capped(events))

    val creativesSortedByScoreDesc =
      eligibleCreatives
        .map(creative => creative -> creative.score(events))
        .sortBy(_._2)(Ordering[Double].reverse)
        .map(_._1)

    Ok(renderVast(creativesSortedByScoreDesc.head, sessionId = UUID.randomUUID().toString))
  }


  def track() = Action { request =>
    logger.info(request.uri)

    val action = request.queryString("action").head
    val sessionId = request.queryString("sessionId").head
    val creativeId = request.queryString("creativeId").head.toLong
    val timestamp = Instant.now()

    val event = TrackingEvent(sessionId, action, timestamp, creativeId)

    if (events.contains(event)) {
      logger.warn("Error, fraud detected!")
    } else {
      events = events :+ event
    }

    logger.info(action)

    Ok
  }

}
