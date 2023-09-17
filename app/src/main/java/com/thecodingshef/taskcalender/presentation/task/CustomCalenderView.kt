package com.thecodingshef.taskcalender.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thecodingshef.taskcalender.R
import java.util.Calendar

@Composable
fun MonthCalendar(
    selectedDay: Int,
    selectedMonth: Int,
    selectedYear: Int,
    onDateClick: (day: Int, month: Int, year: Int) -> Unit
) {
    var currentMonth by remember { mutableStateOf(selectedMonth) }
    var currentYear by remember { mutableStateOf(selectedYear) }

    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        // Month title and navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (currentMonth == 1) {
                        currentMonth = 12
                        currentYear -= 1
                    } else {
                        currentMonth -= 1
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Month"
                )
            }

            Text(
                text = getMonthName(currentMonth),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily(Font(R.font.nunito_medium)), color = Color.Black
                )
            )

            IconButton(
                onClick = {
                    if (currentMonth == 12) {
                        currentMonth = 1
                        currentYear += 1
                    } else {
                        currentMonth += 1
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Month"
                )
            }
        }

        // Days of the week
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
            for (dayOfWeek in daysOfWeek) {
                Text(
                    text = dayOfWeek,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily(Font(R.font.nunito_medium)), color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Calendar grid
        val daysInMonth = getDaysInMonth(currentYear, currentMonth)
        val startOffset = getStartOffset(currentYear, currentMonth)
        val totalDays = daysInMonth + startOffset

        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(totalDays) { day ->
                val dayOfMonth = day - startOffset + 1
                if (dayOfMonth > 0) {
                    DayCell(
                        day = dayOfMonth,
                        month = currentMonth,
                        year = currentYear,
                        isSelected = dayOfMonth == selectedDay && currentMonth == selectedMonth && currentYear == selectedYear,
                        onClick = { clickedDay, clickedMonth, clickedYear ->
                            onDateClick(clickedDay, clickedMonth, clickedYear)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DayCell(
    day: Int,
    month: Int,
    year: Int,
    isSelected: Boolean,
    onClick: (Int, Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .clickable {
                onClick(day, month, year)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (isSelected) Color.White else Color.Black,
                fontFamily = FontFamily(Font(R.font.nunito_medium))
            )
        )
    }
}

@Composable
fun CustomCalendarView(onDateClick: (Int, Int, Int) -> Unit) {
    val currentDate = Calendar.getInstance()
    var selectedDay by remember { mutableStateOf(currentDate.get(Calendar.DAY_OF_MONTH)) }
    var selectedMonth by remember { mutableStateOf(currentDate.get(Calendar.MONTH) + 1) }
    var selectedYear by remember { mutableStateOf(currentDate.get(Calendar.YEAR)) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        MonthCalendar(
            selectedDay = selectedDay,
            selectedMonth = selectedMonth,
            selectedYear = selectedYear,
            onDateClick = { day, month, year ->
                selectedDay = day
                selectedMonth = month
                selectedYear = year
                onDateClick(selectedDay, selectedMonth, selectedYear)
            }
        )
    }
}

@Composable
fun getMonthName(month: Int): String {
    val monthNames = listOf(
        "January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December"
    )
    return monthNames[month - 1]
}

fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun getStartOffset(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)
    return (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
}
