package com.caminero.newton.core.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun daysBetweenDates(startDate : String, endDate : String) : Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val tmpBeginDate = dateFormat.parse(startDate)
    val tmpEndDate = dateFormat.parse(endDate)
    val daysBetweenDates = TimeUnit.MILLISECONDS.toDays(tmpEndDate.time - tmpBeginDate.time)
    return daysBetweenDates.toInt()
}