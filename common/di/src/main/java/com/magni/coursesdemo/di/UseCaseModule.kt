package com.magni.coursesdemo.di

import com.magni.coursesdemo.domain.repo.CourcesRepo
import com.magni.coursesdemo.domain.useCases.GetCourcesUseCase
import com.magni.coursesdemo.domain.useCases.GetFavoritesUseCase
import com.magni.coursesdemo.domain.useCases.UpdateCourseLikeStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCourcesUseCase(repo: CourcesRepo): GetCourcesUseCase {
        return GetCourcesUseCase(repo)
    }

    @Provides
    fun provideUpdateCourseLikeStatusUseCase(repo: CourcesRepo): UpdateCourseLikeStatusUseCase {
        return UpdateCourseLikeStatusUseCase(repo)
    }

    @Provides
    fun provideGetFavoritesUseCase(repo: CourcesRepo): GetFavoritesUseCase {
        return GetFavoritesUseCase(repo)
    }
}