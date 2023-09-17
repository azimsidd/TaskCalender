package com.thecodingshef.taskcalender.base

import com.google.gson.Gson
import com.thecodingshef.taskcalender.model.ErrorResponse
import com.thecodingshef.taskcalender.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import kotlinx.coroutines.flow.Flow

abstract class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): Flow<Resource<T?>> = flow {

        emit(Resource.Loading())
        val response = apiCall()
        if (response.isSuccessful) {
            val data = response.body()
            emit(Resource.Success(data))

        } else {
            val error = response.errorBody()
            try {
                val errorMessage = Gson().fromJson(
                    error?.string(),
                    ErrorResponse::class.java
                )
                emit(Resource.Error(errorMessage.error))
            } catch (e: Exception) {
                emit(Resource.Error("Failed to parse error message"))
            }
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Error(e.message.toString()))
    }.flowOn(dispatcher)
}


