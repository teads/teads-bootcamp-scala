package models

import java.time.Instant


case class TrackingEvent(sessionId: String, action: String, timestamp: Instant, creativeId: Long)