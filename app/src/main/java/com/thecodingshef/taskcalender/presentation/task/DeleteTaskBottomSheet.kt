package com.thecodingshef.taskcalender.presentation.task

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.thecodingshef.taskcalender.R
import com.thecodingshef.taskcalender.presentation.commonComposables.SpacerHeight
import com.thecodingshef.taskcalender.presentation.commonComposables.SpacerWidth

@Composable
fun DeleteTaskBottomSheet(viewModel: TaskViewModel, onCancle: () -> Unit, onConfirm: () -> Unit) {
    val state by viewModel.taskUiState.collectAsState()
    val context = LocalContext.current
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetDeleteTaskState()
            viewModel.getTasks()
        }
    }
    LaunchedEffect(state.deleteTaskState.isTaskDeleted) {
        if (state.deleteTaskState.isTaskDeleted) {
            Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT)
                .show()
            onCancle()
        }
    }
    Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)) {
        Text(
            text = "Are you sure you want to delete?",
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        )
        SpacerHeight(dp = 20.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = { onCancle() },
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, color = Color.Blue),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Cancel",
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium.copy(
                        color = Color.Blue,
                        fontFamily = FontFamily(Font(R.font.nunito_medium))
                    )
                )
            }
            Button(
                onClick = { onConfirm() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Confirm",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.nunito_medium))
                        )
                    )
                    SpacerWidth(dp = 8.dp)
                    if (state.deleteTaskState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    }

                }

            }
        }
    }
}