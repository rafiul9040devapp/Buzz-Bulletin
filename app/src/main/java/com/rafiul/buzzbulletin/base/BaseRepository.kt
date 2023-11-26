package com.rafiul.buzzbulletin.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<ApiState<T>> = flow {

        //initial state would be loading
        emit(ApiState.Loading)

        //then fetch the data
        val response = apiCall()

        if (response.isSuccessful) {
            val dataFromResponse = response.body()
            if (dataFromResponse != null) {
                emit(ApiState.Success(dataFromResponse))
            } else {
                val errorFromResponse = response.errorBody()
                if (errorFromResponse != null) {
                    emit(ApiState.Failure(IOException(errorFromResponse.toString())))
                } else {
                    emit(ApiState.Failure(IOException("Something went wrong.....")))
                }
            }
        } else {
            emit(ApiState.Failure(Throwable(response.errorBody().toString())))
        }

    }.catch { e ->
        e.printStackTrace()
        emit(ApiState.Failure(Exception(e)))

    }.flowOn(Dispatchers.IO)
}