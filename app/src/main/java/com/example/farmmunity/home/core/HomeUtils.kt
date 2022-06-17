package com.example.farmmunity.home.core

import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class HomeUtils {

    companion object {
        fun getFormattedPosted(posted: Long): String =
            PrettyTime(Locale.getDefault()).format(Date(posted))

        fun getFormattedAnswerCount(count: Int): String =
            when (count) {
                0 -> "No Answers"
                1 -> "1 Answer"
                else -> {
                    "$count Answers"
                }
            }
    }
}