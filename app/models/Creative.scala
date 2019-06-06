package models

import concurrent.duration._

final case class Creative(id: Long, url: String) {

}

object Creative {

  val chimeForChange = Creative(1, "http://a.teads.tv/vast/get/1550")
  val dyson = Creative(2, "https://tag.brainient.com/vast/5109981154639872")
  val tiffany = Creative(3, "http://tag.brainient.com/vast/5653714328092672")
  val bizopsnews = Creative(4, "https://tag.brainient.com/vast/5701618635374592")

  val all = List(chimeForChange, dyson, tiffany, bizopsnews)

  def getById(id: Long): Creative = all.find(_.id == id).get
}
