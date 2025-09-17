package com.magni.coursesdemo.di

import com.magni.coursesdemo.domain.repo.CourcesRepo
import com.magni.coursesdemo.data.database.CourceDao
import com.magni.coursesdemo.data.network.CourcesApi
import com.magni.coursesdemo.data.common.CourcesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideCoursesRepo(
        api: CourcesApi,
        courseDao: CourceDao
    ): CourcesRepo {
        return CourcesRepoImpl(
            api,
            courseDao
        )
    }
}