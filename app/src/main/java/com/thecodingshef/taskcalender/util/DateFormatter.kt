package com.thecodingshef.taskcalender.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val SERVER_DATE_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val NEW_SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS Z"
    const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    const val LATEST_NEW_SERVER_DATE_FORMAT1 = "yyyy-MM-ddTHH:mm:ssZ"

    const val DD_MMM_YY = "dd MMM, yy"
    const val DD_DASH_MMM_DASH_YYYY = "dd-MMM-yyyy"
    const val HH_AM_PM = "hh aaa"
    const val HH_MM_AM_PM = "hh:mm aaa"
    const val D_MMMM_YYYY = "dd MMMM, yyyy"
    const val DAY_DATE_MONTH = "EEE, d MMM"
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val D_MMM = "d MMM"
    const val DD_MM_YY = "dd-MM-yy"
    const val DAY_DD_MMM_YYYY = "EEEE, dd MMM - yyyy"


    fun getDateStatus(dateToFormat: String, inputFormat: String) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // For proper conversion, Desired format should be the subset of input format
    fun getFormattedDate(
        dateToFormat: String,
        inputFormat: String = NEW_SERVER_DATE_FORMAT,
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

    fun getDayOfWeekFromDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("dd MMMM - yyyy", Locale.ENGLISH)

        try {
            val date: Date = dateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date

            return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
        } catch (e: Exception) {
            return "Invalid date format: $dateString"
        }
    }

    fun getDateWithMonthDateFormatter(dateStr: String?): String {

        try {
            val inputFormatter = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault())
            val dateObj = inputFormatter.parse(dateStr)
            val outputFormatter = SimpleDateFormat("d MMM", Locale.getDefault())
            return outputFormatter.format(dateObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCurrentDateTime(desiredFormat: String = YYYY_MM_DD_HH_MM_SS): String {
        // Create a formatter for the desired output format
        val formatter = SimpleDateFormat(desiredFormat, Locale.getDefault())

        // Get the current date and time as a Date object
        val now = Date()

        // Format the Date object using the formatter
        val output = formatter.format(now)

        // Return the formatted date string
        return output
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun utcFormatter(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
    }


    fun utcFormatter(dateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val date = inputFormat.parse(dateString)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun formatDateToString(date: Date, desiredPattern: String = YYYY_MM_DD): String {
        try {
            val sdf = SimpleDateFormat(desiredPattern, Locale.getDefault())
            return sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun formatStringToDateObj(dateStr: String, inputPattern: String = YYYY_MM_DD_HH_MM_SS): Date {
        try {
            val sdf = SimpleDateFormat(inputPattern, Locale.getDefault())
            return sdf.parse(dateStr) ?: Date()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Date()
    }

    fun getPreviousDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return calendar.time
    }


    fun formatIntoUiDate(
        inputDateFormat: String = SERVER_DATE_FORMAT,
        inputDateString: String
    ): String {
        val inputFormat = SimpleDateFormat(inputDateFormat, Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val currentDate = Date()
        val utcInputDate = inputFormat.parse(inputDateString)

        Log.d("azeem", "formatIntoUiDate currentDate: $currentDate")

        val timeDifference = currentDate.time - utcInputDate!!.time

        Log.d("azeem", "timeDiff currentDate: $timeDifference")

        val calendar = Calendar.getInstance()
        calendar.time = utcInputDate
        val inputYear = calendar.get(Calendar.YEAR)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val yearDifference = currentYear - inputYear
        val diffMonths =
            (currentYear - inputYear) * 12 + calendar.get(Calendar.MONTH) - Calendar.getInstance()
                .get(Calendar.MONTH)

        val diffSeconds = (timeDifference / 1000) % 60
        val diffMinutes = (timeDifference / (1000 * 60)) % 60
        val diffHours = (timeDifference / (1000 * 60 * 60)) % 24
        val diffDays = (timeDifference / (1000 * 60 * 60 * 24)) % 7
        val diffWeeks = timeDifference / (1000 * 60 * 60 * 24 * 7)
        //  val diffMonths = (timeDifference / (1000 * 60 * 60 * 24 * 30)) % 12

        val valueString = when {
            diffMonths > 0 -> {
                "$diffMonths mo"
            }

            diffWeeks > 0 -> {
                if (diffWeeks > 52) {
                    "$yearDifference y, ${diffWeeks - 52} w"
                } else {
                    "$diffWeeks w"
                }
            }

            diffDays > 0 -> "$diffDays d"
            diffHours > 0 -> {
                "$diffHours h"
            }

            diffMinutes > 0 -> "$diffMinutes m"
            diffSeconds > 0 -> "$diffSeconds s"
            else -> "now"
        }

        Log.d("azeem", "formatIntoUiDate: $valueString")
        return valueString
    }
}

