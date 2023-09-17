package com.thecodingshef.taskcalender.presentation.task.uistate

import com.thecodingshef.taskcalender.model.response.TaskModel
import com.thecodingshef.taskcalender.util.DateFormatter
import com.thecodingshef.taskcalender.util.DateFormatter.DD_MM_YY

data class TaskUiState(
    val calenderState: CalenderState = CalenderState(),
    val taskHomeScreenState: TaskHomeScreenState = TaskHomeScreenState(),
    val addTaskState: AddTaskState = AddTaskState(),
    val deleteTaskState: DeleteTaskState = DeleteTaskState()
)

data class DeleteTaskState(
    val isLoading: Boolean = false,
    val isTaskDeleted: Boolean = false,
    val error: String = ""
)

data class CalenderState(
    val selectedDate: String = DateFormatter.getCurrentDateTime(DD_MM_YY)
)

data class TaskHomeScreenState(
    val isLoading: Boolean = false,
    val taskList: List<TaskModel> = listOf(),
    val error: String = ""
)

data class AddTaskState(
    val isLoading: Boolean = false,
    val isTaskAdded: Boolean = false,
    val error: String = ""
)

enum class TaskScreens(val route: String) {
    TASK_HOME("taskHome"), ADD_TASK_SCREEN("addTaskScreen"), DELETE_BOTTOM_SHEET("deleteBottomSheet")
}
