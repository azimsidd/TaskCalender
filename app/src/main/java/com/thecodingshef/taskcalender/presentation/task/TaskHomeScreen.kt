package com.thecodingshef.taskcalender.presentation.task

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.thecodingshef.taskcalender.presentation.commonComposables.SpacerHeight
import com.thecodingshef.taskcalender.presentation.commonComposables.toColor


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskHomeScreen(
    viewModel: TaskViewModel,
    addTask: () -> Unit,
    onTaskDelete: (taskId: Int) -> Unit,
    onDateClick: (day: Int, month: Int, year: Int) -> Unit
) {
    val state by viewModel.taskUiState.collectAsState()
    val context = LocalContext.current
    var refreshing by remember {
        mutableStateOf(false)
    }

    val pullRefreshState = rememberPullRefreshState(refreshing, {
        refreshing = true
        viewModel.getTasks()
        refreshing = false
    })
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(10.dp)
        ) {
            CustomCalendarView(onDateClick = { day, month, year ->
                onDateClick(day, month, year)
            })
            SpacerHeight(dp = 20.dp)
            if (state.taskHomeScreenState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            } else if (state.taskHomeScreenState.error.isNotEmpty()) {
                Toast.makeText(context, state.taskHomeScreenState.error, Toast.LENGTH_SHORT).show()
            } else {
                Box(
                    Modifier
                        .pullRefresh(pullRefreshState)
                        .padding(vertical = 10.dp)
                        .weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        items(state.taskHomeScreenState.taskList, key = {
                            it.taskId
                        }) { task ->
                            TaskItem(task = task, onDelete = { taskId ->
                                onTaskDelete(taskId)
                            })

                        }
                    }
                    PullRefreshIndicator(
                        refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter)
                    )
                }

            }

        }

        FloatingActionButton(
            onClick = { addTask() },
            shape = RoundedCornerShape(8.dp),
            containerColor = "#FFFFE0".toColor(),
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier
                .align(BottomEnd)
                .padding(30.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "", Modifier.size(34.dp))
        }
    }

}
