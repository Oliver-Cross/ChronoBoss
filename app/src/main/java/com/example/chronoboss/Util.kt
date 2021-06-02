package com.example.chronoboss

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.chronoboss.database.Day


/**
 * Displays all data from the database.
 */
fun formatDays(days: List<Day>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        days.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("Time Wasted: ")
            append("\t ${it.timeWasted}")
            append("Day ID: ")
            append("\t ${it.dayId}")
            append("Application: ")
            append("\t ${it.application}")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

/*
fun formatTodayLimit(days: List<Day>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("Current time limit: ${days[0].timeLimit}")
    }
    return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}

 */

fun formatTodayLimit(day: Day, resources: Resources): String {
    val sb = StringBuilder()
    sb.apply {
        append("Current time limit: ${day.timeLimit}")
    }
    return sb.toString()
}



/*
fun formatTodayUsage(days: List<Day>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("Current Wasted: ${days[0].timeWasted}")
    }
    return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}
*/

fun formatTodayUsage(day: Day, resources: Resources): String {
    val sb = StringBuilder()
    sb.apply {
        append("Current Wasted: ${day.timeWasted}")
    }
    return sb.toString()
}

fun formatPercentage(day: Day, resources: Resources): String {
    val sb = StringBuilder()
    sb.apply {
        append("Time wasted: ${day.timeWasted}   ")
        append("Time limit: ${day.timeLimit}   ")
        append("Time wasted / Time limit: ${day.timeWasted.toFloat()/day.timeLimit.toFloat()}   ")
        append("Time limit -  time wasted: ${day.timeLimit - day.timeWasted}   ")
        append("${100 * (day.timeWasted.toFloat() / day.timeLimit.toFloat())}")
    }
    return sb.toString()
}
