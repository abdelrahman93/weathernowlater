package com.task.core.common.util

fun unixToTime(unix: Long): String {
    val date = java.util.Date(unix * 1000)
    val format = java.text.SimpleDateFormat("HH:mm")
    format.timeZone = java.util.TimeZone.getDefault()
    return format.format(date)
}