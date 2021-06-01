package com.example.chronoboss

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.chronoboss.database.Day

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