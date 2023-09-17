package com.thecodingshef.taskcalender.model.request

import com.thecodingshef.taskcalender.model.response.Task

data class AddTaskRequest(
    val task: Task,
    val user_id: Int
)