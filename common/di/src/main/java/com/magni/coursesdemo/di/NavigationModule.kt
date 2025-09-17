package com.magni.coursesdemo.di

import com.magni.coursesdemo.di.navigation.AppFragmentProvider
import com.magni.coursesdemo.di.navigation.AppNavigator
import com.magni.coursesdemo.presentation.FragmentContract
import com.magni.coursesdemo.presentation.NavigationContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindNavigationContract(appNavigator: AppNavigator): NavigationContract

    @Binds
    abstract fun bindFragmentContract(appFragmentProvider: AppFragmentProvider): FragmentContract
}