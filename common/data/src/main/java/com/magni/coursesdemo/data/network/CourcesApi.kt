package com.magni.coursesdemo.data.network

import retrofit2.http.GET

interface CourcesApi {
    @GET("courses")
    suspend fun getCourses(): CoursesResponse
}