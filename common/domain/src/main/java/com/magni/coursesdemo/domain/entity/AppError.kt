package com.magni.coursesdemo.domain.entity

import java.io.IOException
import java.time.DateTimeException

sealed class AppError : Exception() {
    data class NetworkError(override val cause: Throwable? = null) : AppError()
    data class DataParsingError(override val cause: Throwable? = null) : AppError()
    data class DataConversionError(override val cause: Throwable? = null) : AppError()
    data class DatabaseError(override val cause: Throwable? = null) : AppError()
    data class UnknownError(override val cause: Throwable? = null) : AppError()

    companion object {
        fun fromThrowable(throwable: Throwable, source: String? = null): AppError {
            return when (throwable) {
                is AppError -> throwable
                is IOException -> NetworkError(throwable)
                is IllegalArgumentException -> DataParsingError(throwable)
                is DateTimeException -> DataConversionError(throwable)
                else -> UnknownError(throwable)
            }
        }
    }
}