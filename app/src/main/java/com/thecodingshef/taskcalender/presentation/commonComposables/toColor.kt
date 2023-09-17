package com.thecodingshef.taskcalender.presentation.commonComposables

import androidx.compose.ui.graphics.Color

fun String?.toColor(): Color {
    return try {
        if (!this.isNullOrEmpty()) {
            Color(android.graphics.Color.parseColor(this))
        } else {
            Color.Transparent
        }
    } catch (e: Exception) {
        Color.Transparent
    }
}