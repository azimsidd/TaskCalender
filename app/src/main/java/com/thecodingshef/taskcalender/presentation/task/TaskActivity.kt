package com.thecodingshef.taskcalender.presentation.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.thecodingshef.taskcalender.presentation.task.uistate.TaskScreens
import com.thecodingshef.taskcalender.ui.theme.TaskCalenderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskCalenderTheme {
                val viewModel: TaskViewModel = hiltViewModel()
                TaskNavGraph(viewModel,
                    startDestination = TaskScreens.TASK_HOME.route,
                    onFinish = { finish() })
            }
        }
    }
}
