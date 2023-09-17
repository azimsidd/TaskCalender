package com.thecodingshef.taskcalender.model.response

import com.google.gson.annotations.SerializedName

data class TaskModel(
    @SerializedName("task_detail")
    val taskDetail: Task,
    @SerializedName("task_id")
    val taskId: Int
)