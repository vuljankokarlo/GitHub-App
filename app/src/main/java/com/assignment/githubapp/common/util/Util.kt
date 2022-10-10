package com.assignment.githubapp.common.util

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import com.assignment.githubapp.common.util.Util.Companion.API_DATE_FORMAT
import com.assignment.githubapp.common.util.Util.Companion.PRESENTATION_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        const val PRESENTATION_DATE_FORMAT = "dd.MM.yyyy"
        const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        enum class FlavorType {
            FREE,
            PAID;

            override fun toString(): String {
                return name.lowercase()
            }
        }
    }
}

fun Int.dpFromPx(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

fun String.parseToPresentationFormat(): String {
    if (this.isEmpty())
        return ""
    val date = this.getDate()
    return date.parseToPresentationFormat()
}

fun Date.parseToPresentationFormat(): String =
    SimpleDateFormat(PRESENTATION_DATE_FORMAT, Locale.getDefault()).format(this)

fun String.getDate(format: String = API_DATE_FORMAT): Date {
    return SimpleDateFormat(format).parse(this)
}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
