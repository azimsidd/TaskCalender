package com.thecodingshef.taskcalender.services

import com.thecodingshef.taskcalender.model.request.AddTaskRequest
import com.thecodingshef.taskcalender.model.request.DeleteTaskRequest
import com.thecodingshef.taskcalender.model.response.SuccessResponse
import com.thecodingshef.taskcalender.model.response.TaskListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/storeCalendarTask")
    suspend fun addTask(@Body task: AddTaskRequest): Response<SuccessResponse>

    @POST("api/getCalendarTaskList")
    suspend fun getAllTasks(@Body userId: UserRequest = UserRequest(user_id = 1)): Response<TaskListResponse>

    @POST("api/deleteCalendarTask")
    suspend fun deleteTask(@Body request: DeleteTaskRequest): Response<SuccessResponse>
}


data class UserRequest(
    val user_id:Int
)