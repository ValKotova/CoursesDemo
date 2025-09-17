package com.magni.coursesdemo.domain.useCases

import com.magni.coursesdemo.domain.entity.Course
import com.magni.coursesdemo.domain.repo.CourcesRepo

class GetFavoritesUseCase constructor(
    private val repo: CourcesRepo
) : BaseUseCase<Unit, List<Course>>() {

    override suspend fun execute(params: Unit): Result<List<Course>> {
        return repo.getFavoriteCourses()
    }
}