package com.thecodingshef.taskcalender.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    const val DD_MMM_YY = "dd MMM, yy"
    const val DD_MM_YY = "dd-MM-yy"
    const val DAY_DD_MMM_YYYY = "EEEE, dd MMM - yyyy"

    // For proper conversion, Desired format should be the subset of input format
    fun getFormattedDate(
        dateToFormat: String,
        inputFormat: String = DD_MM_YY,
        desiredDateFormat: String
    ): String {
        try {
            val sdf = SimpleDateFormat(inputFormat, Locale.getDefault())
            val output = SimpleDateFormat(desiredDateFormat, Locale.getDefault())
            val d = sdf.parse(dateToFormat)
            return output.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCurrentDateTime(desiredFormat: String = DD_MM_YY): String {
        // Create a formatter for the desired output format
        val formatter = SimpleDateFormat(desiredFormat, Locale.getDefault())

        // Get the current date and time as a Date object
        val now = Date()

        // Format the Date object using the formatter
        val output = formatter.format(now)

        // Return the formatted date string
        return output
    }

}

