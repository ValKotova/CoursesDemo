package com.magni.coursesdemo.presentation

import android.content.Context
import com.magni.coursesdemo.domain.entity.AppError

fun getErrorMessage(error: AppError, context: Context) = when (error) {
    is AppError.NetworkError -> context.getString(R.string.error_network_message)
    is AppError.DatabaseError -> context.getString(R.string.error_database_message)
    is AppError.DataConversionError -> context.getString(R.string.error_generic)
    else -> context.getString(R.string.error_database_message)
}