package com.magni.coursesdemo.presentation

import android.content.Context

interface NavigationContract {
    fun navigateToHome(context: Context)
    fun navigateToAuth(context: Context)
    fun navigateToFavorites(context: Context)
    fun navigateToProfile(context: Context)
}