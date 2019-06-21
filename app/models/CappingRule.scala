package models

import java.time.Instant
import java.time.temporal.ChronoUnit

import scala.concurrent.duration.FiniteDuration


case class CappingRule(period: FiniteDuration, threshold: Int)