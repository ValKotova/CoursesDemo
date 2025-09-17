package com.magni.coursesdemo.domain.useCases

import com.magni.coursesdemo.domain.repo.CourcesRepo

class UpdateCourseLikeStatusUseCase constructor(
    private val repo: CourcesRepo
) : BaseUseCase<Pair<Int, Boolean>, Unit>() {

    override suspend fun execute(params: Pair<Int, Boolean>): Result<Unit> {
        return repo.updateCourseLikeStatus(params.first, params.second)
    }
}