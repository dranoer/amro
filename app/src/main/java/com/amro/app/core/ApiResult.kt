package com.amro.app.core

/**
 * A sealed class that encapsulates the result of an API call,
 * which can be either a success with data or a failure with an error.
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val type: ErrorType) : ApiResult<Nothing>()
}