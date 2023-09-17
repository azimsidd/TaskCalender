package com.thecodingshef.taskcalender.presentation.task

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.thecodingshef.taskcalender.model.request.AddTaskRequest
import com.thecodingshef.taskcalender.model.request.DeleteTaskRequest
import com.thecodingshef.taskcalender.presentation.task.uistate.TaskScreens
import com.thecodingshef.taskcalender.util.Constants.USER_ID

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun TaskNavGraph(viewModel: TaskViewModel, startDestination: String, onFinish: () -> Unit) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val context = LocalContext.current

    val state by viewModel.taskUiState.collectAsState()
    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(navController = navController, startDestination = startDestination) {

            composable(route = TaskScreens.TASK_HOME.route) {
                LaunchedEffect(Unit) {
                    viewModel.getTasks()
                }
                TaskHomeScreen(
                    viewModel = viewModel,
                    addTask = { navController.navigate(TaskScreens.ADD_TASK_SCREEN.route) },
                    onTaskDelete = { taskId ->
                        navController.navigate(TaskScreens.DELETE_BOTTOM_SHEET.route + "/" + taskId.toString())
                    },
                    onDateClick = { day, month, year ->
                        viewModel.setSelectedDate("$day-$month-$year")
                    })

            }
            composable(route = TaskScreens.ADD_TASK_SCREEN.route) {
                BackHandler {
                    navController.popBackStack()
                }
                AddTaskScreen(viewModel,
                    onClose = { navController.popBackStack() },
                    onTaskSubmit = { task ->
                        viewModel.addTask(AddTaskRequest(task = task, user_id = USER_ID))
                    })
            }

            bottomSheet(route = TaskScreens.DELETE_BOTTOM_SHEET.route + "/" + "{taskId}") { backStackEntry ->
                BackHandler {
                    navController.popBackStack()
                }
                backStackEntry.arguments?.getString("taskId")?.let { taskId ->
                    DeleteTaskBottomSheet(
                        viewModel = viewModel,
                        onCancle = {
                            navController.popBackStack()
                        }, onConfirm = {
                            viewModel.deleteTask(
                                DeleteTaskRequest(
                                    taskId = taskId.toInt(),
                                    userId = USER_ID
                                )
                            )
                        })
                }
            }
        }
    }

}