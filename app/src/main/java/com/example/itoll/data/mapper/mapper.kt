package com.example.itoll.data.mapper

import com.example.itoll.domain.model.ResultData
import retrofit2.Response

inline fun <T> execute(request: () -> Response<T>): ResultData<T> =
    try {
        val response = request()
        if (response.isSuccessful) {
            ResultData.Success(response.body()!!)
        } else {
            ResultData.Failure(response.message())
        }
    } catch (ex: Exception) {
        ResultData.Error(ex)
    }
