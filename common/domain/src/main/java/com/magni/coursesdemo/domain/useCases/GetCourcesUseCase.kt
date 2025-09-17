package com.magni.coursesdemo.domain.useCases

import com.magni.coursesdemo.domain.entity.Course
import com.magni.coursesdemo.domain.repo.CourcesRepo

class GetCourcesUseCase constructor(
    private val repo: CourcesRepo
) : BaseUseCase<Boolean, List<Course>>() {

    override suspend fun execute(refresh: Boolean): Result<List<Course>> {
        return repo.getCourses(refresh)
    }
}
