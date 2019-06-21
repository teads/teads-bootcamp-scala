package models

import java.time.Instant

import concurrent.duration._

final case class Creative(id: Long, url: String, revenue: Int, cappingRule: Option[CappingRule] = Option.empty) {

  def capped(events: Vector[TrackingEvent]): Boolean = {
    cappingRule match {
      case Some(rule) =>
        val deadline: Instant = Instant.now.minusMillis(rule.period.toMillis)

        val impressions: Int = events.count { event =>
          event.action == "impression" &&
            event.creativeId == this.id &&
            event.timestamp.isAfter(deadline)
        }

        impressions >= rule.threshold

      case None => false

    }
  }

  def score(events: Vector[TrackingEvent]): Double = {
    val (impressionsCount, completesCount) = events
      .filter(_.creativeId == this.id)
      .foldLeft((0, 0)) {
        case ((impressions, completes), event) =>
          if (event.action == "impression") {
            (impressions + 1, completes)
          } else if (event.action == "complete") {
            (impressions, completes + 1)
          } else {
            (impressions, completes)
          }
      }

    val completionRate: Double = if (impressionsCount == 0) {
      1
    } else {
      completesCount.toDouble / impressionsCount
    }

    completionRate * revenue
  }

}

object Creative {

  val chimeForChange = Creative(1, "http://a.teads.tv/vast/get/1550", revenue = 1)
  val dyson = Creative(2, "https://tag.brainient.com/vast/5109981154639872", revenue = 1, Option(CappingRule(55.minutes, 1)))
  val tiffany = Creative(3, "http://tag.brainient.com/vast/5653714328092672", revenue = 3)
  val bizopsnews = Creative(4, "https://tag.brainient.com/vast/5701618635374592", revenue = 2)

  val all = List(chimeForChange, dyson, tiffany, bizopsnews)

  def getById(id: Long): Creative = all.find(_.id == id).get
}
