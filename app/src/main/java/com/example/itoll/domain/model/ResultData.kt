package com.example.itoll.domain.model

import kotlin.jvm.Throws

sealed class ResultData<out T> {

    data class Success<out T>(val value: T) : ResultData<T>()
    data class Failure<out T>(val message: String) : ResultData<T>()
    data class Error(val throws: Throwable) : ResultData<Nothing>()
}

fun <T> ResultData<T>.onSuccess(block: (T) -> Unit): ResultData<T> {
    if (this is ResultData.Success) {
        block(this.value)
    }
    return this
}

fun <T, O> ResultData<T>.map(block: (T) -> O): ResultData<O> =
    when (this) {
        is ResultData.Success -> ResultData.Success(block(value))
        is ResultData.Failure -> ResultData.Failure(message)
        is ResultData.Error -> ResultData.Error(throws)
    }