package com.amro.app.core

import java.io.IOException

sealed class ErrorType {
    object Network : ErrorType()
    object Invalid : ErrorType()
}

fun ErrorType.toUserMessage(): String = when (this) {
    ErrorType.Network -> "No internet connection."
    ErrorType.Invalid -> "Something went wrong."
}

fun Throwable.toErrorType(): ErrorType = when (this) {
    is IOException -> ErrorType.Network
    else -> ErrorType.Invalid
}