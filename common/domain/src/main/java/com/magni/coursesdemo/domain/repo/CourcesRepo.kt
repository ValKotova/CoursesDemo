package com.magni.coursesdemo.domain.repo

import com.magni.coursesdemo.domain.entity.Course

interface CourcesRepo {
    suspend fun getCourses(refresh: Boolean = false): Result<List<Course>>
    suspend fun updateCourseLikeStatus(id: Int, isLiked: Boolean): Result<Unit>
    suspend fun getFavoriteCourses(): Result<List<Course>>
}