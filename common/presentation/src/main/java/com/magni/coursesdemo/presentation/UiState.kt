package com.magni.coursesdemo.presentation

import com.magni.coursesdemo.domain.entity.AppError

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: AppError) : UiState<Nothing>
}

fun <T> Result<T>.toUiState(): UiState<T> {
    return fold(
        onSuccess = { UiState.Success(it) },
        onFailure = {
            UiState.Error(
                if (it is AppError) it
                else AppError.fromThrowable(it)
            )
        }
    )
}