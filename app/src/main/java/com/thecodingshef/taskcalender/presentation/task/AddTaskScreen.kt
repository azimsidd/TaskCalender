package com.thecodingshef.taskcalender.presentation.task

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.thecodingshef.taskcalender.R
import com.thecodingshef.taskcalender.model.response.Task
import com.thecodingshef.taskcalender.presentation.commonComposables.SpacerHeight
import com.thecodingshef.taskcalender.util.DateFormatter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel, onClose: () -> Unit, onTaskSubmit: (task: Task) -> Unit
) {
    val state by viewModel.taskUiState.collectAsState()
    val keybaordController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetAddTaskState()
        }
    }

    LaunchedEffect(state.addTaskState.isTaskAdded) {
        if (state.addTaskState.isTaskAdded) {
            Toast.makeText(context, "Task Added Success", Toast.LENGTH_SHORT).show()
            onClose()
        }
    }

    var titleValue by remember {
        mutableStateOf("")
    }

    var descriptionValue by remember {
        mutableStateOf("")
    }
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp)
            ) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = "close icon",
                    modifier = Modifier
                        .align(
                            CenterStart
                        )
                        .clickable { onClose() })
                TextButton(
                    onClick = {
                        keybaordController?.hide()
                        onTaskSubmit(
                            Task(
                                title = titleValue,
                                description = descriptionValue,
                                date = state.calenderState.selectedDate,
                                created = DateFormatter.getCurrentDateTime(DateFormatter.DD_MM_YY)
                            )
                        )
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .align(
                            CenterEnd
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    border = BorderStroke(width = 1.dp, color = Color.White),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(R.font.nunito_medium)
                            )
                        )
                    )
                }
            }

            BasicTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                value = titleValue,
                onValueChange = {
                    titleValue = it
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(color = Color.Gray),
                maxLines = 2,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { inlineTextField ->
                    AnimatedVisibility(visible = titleValue.isBlank()) {
                        Text(
                            text = "Add Title",
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    inlineTextField()
                })
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            SpacerHeight(dp = 20.dp)

            BasicTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                value = descriptionValue,
                onValueChange = {
                    descriptionValue = it
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { inlineTextField ->
                    AnimatedVisibility(visible = descriptionValue.isBlank()) {
                        Text(
                            text = "Describe your task",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    inlineTextField()
                })
        }
        if (state.addTaskState.isLoading) {
            CircularProgressIndicator()
        }
    }
}