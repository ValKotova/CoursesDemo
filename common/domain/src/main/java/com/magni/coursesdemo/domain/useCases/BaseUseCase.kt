package com.magni.coursesdemo.domain.useCases

import com.magni.coursesdemo.domain.entity.AppError

abstract class BaseUseCase<in P, out R> {
    suspend operator fun invoke(params: P): Result<R> {
        return try {
            execute(params)
        } catch (e: Exception) {
            Result.failure(AppError.fromThrowable(e, this::class.simpleName))
        }
    }

    @Throws(Exception::class)
    protected abstract suspend fun execute(params: P): Result<R>
}