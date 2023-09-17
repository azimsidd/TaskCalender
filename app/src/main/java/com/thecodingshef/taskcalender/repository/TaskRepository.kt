package com.thecodingshef.taskcalender.repository

import com.thecodingshef.taskcalender.base.BaseRepository
import com.thecodingshef.taskcalender.model.request.DeleteTaskRequest
import com.thecodingshef.taskcalender.services.ApiService
import com.thecodingshef.taskcalender.model.request.AddTaskRequest
import javax.inject.Inject

class TaskRepository @Inject constructor(private val api: ApiService) : BaseRepository() {

    suspend fun addTask(task: AddTaskRequest) = safeApiCall {
        api.addTask(task)
    }

    suspend fun getAllTasks() = safeApiCall {
        api.getAllTasks()
    }

    suspend fun deleteTask(request: DeleteTaskRequest) = safeApiCall {
        api.deleteTask(request)
    }

}