package com.magni.coursesdemo.data.common

import com.magni.coursesdemo.data.database.CourceDao
import com.magni.coursesdemo.data.database.toEntity
import com.magni.coursesdemo.data.network.CourcesApi
import com.magni.coursesdemo.domain.entity.AppError
import com.magni.coursesdemo.domain.entity.Course
import com.magni.coursesdemo.domain.repo.CourcesRepo

class CourcesRepoImpl(
    private val api: CourcesApi,
    private val courceDao: CourceDao,
) : CourcesRepo {

    override suspend fun getCourses(refresh: Boolean): Result<List<Course>> = runCatching {

        if (!refresh) {
            val cachedCourses = getCachedCourses()
            if (cachedCourses.isNotEmpty()) {
                return Result.success(cachedCourses)
            }
        }

        val response = api.getCourses()
        val courses = response.courses.map { it.toDomain() }

        cacheCourses(courses)

        courses
    }.recoverCatching { e ->
        // If network fails, try to get from database
        val cachedCourses = getCachedCourses()
        if (cachedCourses.isNotEmpty()) {
            cachedCourses
        } else {
            throw AppError.fromThrowable(e, "CourcesRepoImpl")
        }
    }

    override suspend fun updateCourseLikeStatus(id: Int, isLiked: Boolean): Result<Unit> =
        runCatching {
            courceDao.updateLikeStatus(id, isLiked)
        }.recoverCatching { e ->
            throw AppError.DatabaseError(e)
        }

    private suspend fun cacheCourses(courses: List<Course>) {
        try {
            courceDao.insertAll(courses.map { it.toEntity() })
        } catch (e: Exception) {
            throw AppError.DatabaseError(e)
        }
    }

    private suspend fun getCachedCourses(): List<Course> {
        return try {
            courceDao.getAll().map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getFavoriteCourses(): Result<List<Course>> = runCatching {
        val favoriteEntities = courceDao.getFavoriteCourses()
        favoriteEntities.map { it.toDomain() }
    }.recoverCatching { e ->
        throw AppError.DatabaseError(e)
    }
}