package com.thecodingshef.taskcalender.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecodingshef.taskcalender.repository.TaskRepository
import com.thecodingshef.taskcalender.util.Resource
import com.thecodingshef.taskcalender.model.request.AddTaskRequest
import com.thecodingshef.taskcalender.model.request.DeleteTaskRequest
import com.thecodingshef.taskcalender.presentation.task.uistate.AddTaskState
import com.thecodingshef.taskcalender.presentation.task.uistate.CalenderState
import com.thecodingshef.taskcalender.presentation.task.uistate.DeleteTaskState
import com.thecodingshef.taskcalender.presentation.task.uistate.TaskHomeScreenState
import com.thecodingshef.taskcalender.presentation.task.uistate.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) :
    ViewModel() {

    private val _taskUiState = MutableStateFlow(TaskUiState())
    val taskUiState: StateFlow<TaskUiState> = _taskUiState

    fun getTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { response ->
                when (response) {
                    is Resource.Loading -> {
                        _taskUiState.value = _taskUiState.value.copy(
                            taskHomeScreenState = TaskHomeScreenState(isLoading = true)
                        )
                    }

                    is Resource.Success -> {
                        response.data?.let { data ->
                            _taskUiState.value = _taskUiState.value.copy(
                                taskHomeScreenState = _taskUiState.value.taskHomeScreenState.copy(
                                    isLoading = false,
                                    taskList = data.tasks.reversed()
                                )
                            )
                        }
                    }

                    is Resource.Error -> {
                        _taskUiState.value = _taskUiState.value.copy(
                            taskHomeScreenState = _taskUiState.value.taskHomeScreenState.copy(
                                isLoading = false, error = response.message.orEmpty()
                            )
                        )
                    }
                }
            }
        }
    }

    fun addTask(task: AddTaskRequest) {
        viewModelScope.launch {
            repository.addTask(task = task).collectLatest { response ->
                when (response) {
                    is Resource.Loading -> {
                        _taskUiState.value = _taskUiState.value.copy(
                            addTaskState = AddTaskState(isLoading = true)
                        )
                    }

                    is Resource.Success -> {
                        response.data?.let { data ->
                            _taskUiState.value = _taskUiState.value.copy(
                                addTaskState = _taskUiState.value.addTaskState.copy(
                                    isLoading = false,
                                    isTaskAdded = true
                                )
                            )
                        }

                    }

                    is Resource.Error -> {
                        _taskUiState.value = _taskUiState.value.copy(
                            addTaskState = _taskUiState.value.addTaskState.copy(
                                isLoading = false,
                                error = response.message.orEmpty()
                            )
                        )
                    }
                }
            }
        }
    }


    fun deleteTask(request: DeleteTaskRequest) {
        viewModelScope.launch {
            repository.deleteTask(request = request).collectLatest { response ->
                when (response) {
                    is Resource.Loading -> {
                        _taskUiState.value = _taskUiState.value.copy(
                            deleteTaskState = _taskUiState.value.deleteTaskState.copy(
                                isLoading = true
                            )
                        )
                    }

                    is Resource.Success -> {
                        response.data?.let { data ->
                            _taskUiState.value = _taskUiState.value.copy(
                                deleteTaskState = _taskUiState.value.deleteTaskState.copy(
                                    isLoading = false,
                                    isTaskDeleted = true
                                )
                            )
                        }

                    }

                    is Resource.Error -> {
                        _taskUiState.value = _taskUiState.value.copy(
                            deleteTaskState = _taskUiState.value.deleteTaskState.copy(
                                isLoading = false
                            )
                        )
                    }
                }
            }
        }
    }

    fun resetAddTaskState() {
        _taskUiState.value = _taskUiState.value.copy(
            addTaskState = AddTaskState()
        )
    }

    fun resetDeleteTaskState() {
        _taskUiState.value = _taskUiState.value.copy(
            deleteTaskState = DeleteTaskState()
        )
    }

    fun setSelectedDate(date: String) {
        _taskUiState.value = _taskUiState.value.copy(
            calenderState = CalenderState(selectedDate = date)
        )
    }
}
