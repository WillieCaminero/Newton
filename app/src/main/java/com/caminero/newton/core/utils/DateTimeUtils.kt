package com.caminero.newton.core.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private const val baseDateFormat = "dd-MM-yyyy"
private const val baseDateApiFormat = "yyyy-MM-dd'T'HH:mm:ss"

fun convertStringDateTimeISO8601ToStringDateTime(stringDate: String): String {
    val dateFormat = SimpleDateFormat(baseDateApiFormat, Locale.getDefault())
    return SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(dateFormat.parse(stringDate))
}

fun convertStringDateTimeISO8601ToStringTime(stringDate: String): String {
    val dateFormat = SimpleDateFormat(baseDateApiFormat, Locale.getDefault())
    return SimpleDateFormat("hh:mm:ss aa", Locale.getDefault()).format(dateFormat.parse(stringDate))
}

fun convertStringDateTimeISO8601ToDate(stringDate: String): Date {
    val dateFormat = SimpleDateFormat(baseDateApiFormat, Locale.getDefault())
    return dateFormat.parse(stringDate)
}

fun convertStringDateToDate(stringDate: String): Date {
    val dateFormat = SimpleDateFormat(baseDateFormat, Locale.getDefault())
    return dateFormat.parse(stringDate)
}

fun convertStringDateTimeISO8601ToStringDate(stringDate: String): String {
    val dateFormat = SimpleDateFormat(baseDateApiFormat, Locale.getDefault())
    return SimpleDateFormat(baseDateFormat, Locale.getDefault()).format(dateFormat.parse(stringDate))
}

fun convertStringDateToStringDateTimeISO8601(stringDate: String): String {
    val currentDate = Calendar.getInstance()
    val hour = completeIntegerWithZero(currentDate.get(Calendar.HOUR_OF_DAY))
    val minute = completeIntegerWithZero(currentDate.get(Calendar.MINUTE))
    val second = completeIntegerWithZero(currentDate.get(Calendar.SECOND))

    val dateTime = "${stringDate}T$hour:$minute:$second"

    val dateFormat = SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss", Locale.getDefault())

    return SimpleDateFormat(baseDateApiFormat, Locale.getDefault()).format(dateFormat.parse(dateTime))
}

fun convertStringDateToStringDateTimeISO8601TimeZone(stringDate: String): String {
    val dateTime = "${stringDate}T00:00:00${timeZone()}"
    val dateFormat = SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ssZ", Locale.getDefault())
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(dateFormat.parse(dateTime))
}

fun timeZone(): String {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault())
    val timeZone = SimpleDateFormat("Z").format(calendar.time)
    return timeZone.substring(0, 3) + ":" + timeZone.substring(3, 5)
}

fun daysBetweenDates(startDate : String, endDate : String) : Int {
    val dateFormat = SimpleDateFormat(baseDateFormat, Locale.getDefault())
    val tmpBeginDate = dateFormat.parse(startDate)
    val tmpEndDate = dateFormat.parse(endDate)
    val daysBetweenDates = TimeUnit.MILLISECONDS.toDays(tmpEndDate.time - tmpBeginDate.time)
    return (daysBetweenDates + 1).toInt()
}

fun completeIntegerWithZero(value: Int): String = if(value <= 9) "0$value" else value.toString()

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.addYears(years: Int): Date{
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date{
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date{
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date{
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date{
    return add(Calendar.SECOND, seconds)
}