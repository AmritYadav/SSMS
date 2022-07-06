package com.sts.ssms.utils

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

private const val EVENT_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm a"
private const val EVENT_DATE_FORMAT_1 = "yyyy-MM-dd"
private const val EVENT_DATE_FORMAT_2 = "dd-MM-yyyy"
private const val EVENT_DATE_FORMAT = "E dd MMM, yyyy"
private const val EVENT_TIME_FORMAT = "hh:mm a" // 12 hrs format

fun DateTime.seconds() = millis / 1000L

fun eventFormatter(): DateTimeFormatter = DateTimeFormat.forPattern(EVENT_DATE_TIME_FORMAT)

fun eventFormatterSociety(): DateTimeFormatter = DateTimeFormat.forPattern(EVENT_DATE_FORMAT_1)

fun eventDateFormatter(): DateTimeFormatter = DateTimeFormat.forPattern(EVENT_DATE_FORMAT)

fun eventDateFormatter2(): DateTimeFormatter = DateTimeFormat.forPattern(EVENT_DATE_FORMAT_2)

private fun eventTimeFormatter(): DateTimeFormatter = DateTimeFormat.forPattern(EVENT_TIME_FORMAT)

fun getEventDateTimeInSeconds(input: String, formatter: DateTimeFormatter): Long {
    val dateTime = LocalDateTime.parse(input, formatter).toDateTime()
    return dateTime.withSecondOfMinute(0).withMillisOfSecond(0).seconds()
}

fun LocalDate.toStringEventDate(): String = this.toString(eventDateFormatter())

fun LocalDate.toStringEventDate1(): String = this.toString(eventDateFormatter2())

fun LocalTime.toStringEventTime(): String = this.toString(eventTimeFormatter())