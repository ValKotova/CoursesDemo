package com.magni.coursesdemo.data.network

import retrofit2.http.GET

interface CourcesApi {
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q")
    suspend fun getCourses(): CoursesResponse
}