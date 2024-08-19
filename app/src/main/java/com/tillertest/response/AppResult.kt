package com.tillertest.response

sealed class AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Error(val exception: Throwable?, val message: String = "Unknown error") :
        AppResult<Nothing>()
}