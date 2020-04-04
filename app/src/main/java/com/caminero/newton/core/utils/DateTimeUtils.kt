package com.caminero.newton.core.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun daysBetweenDates(startDate : String, endDate : String) : Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val tmpBeginDate = dateFormat.parse(startDate)
    val tmpEndDate = dateFormat.parse(endDate)
    val daysBetweenDates = TimeUnit.MILLISECONDS.toDays(tmpEndDate.time - tmpBeginDate.time)
    return (daysBetweenDates + 1).toInt()
}

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