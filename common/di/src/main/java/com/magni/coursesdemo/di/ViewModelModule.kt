package com.magni.coursesdemo.di

import com.magni.coursesdemo.feature.favorites.FavoritesViewModel
import com.magni.coursesdemo.feature.home.presentation.HomeViewModel
import com.magni.coursesdemo.feature.profile.ProfileViewModel
import com.magni.coursesdemo.domain.useCases.GetCourcesUseCase
import com.magni.coursesdemo.domain.useCases.GetFavoritesUseCase
import com.magni.coursesdemo.domain.useCases.UpdateCourseLikeStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideHomeViewModel(
        getCourcesUseCase: GetCourcesUseCase,
        updateCourseLikeStatusUseCase: UpdateCourseLikeStatusUseCase
    ): HomeViewModel = HomeViewModel(getCourcesUseCase, updateCourseLikeStatusUseCase)

    @Provides
    @ViewModelScoped
    fun provideFavoritesViewModel(
        getFavoritesUseCase: GetFavoritesUseCase,
        updateCourseLikeStatusUseCase: UpdateCourseLikeStatusUseCase
    ): FavoritesViewModel = FavoritesViewModel(
        getFavoritesUseCase,
        updateCourseLikeStatusUseCase
    )

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(
        getCourcesUseCase: GetCourcesUseCase
    ): ProfileViewModel = ProfileViewModel(getCourcesUseCase)
}