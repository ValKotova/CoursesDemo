package com.magni.coursesdemo.di.navigation

import android.content.Context
import android.content.Intent
import com.magni.coursesdemo.presentation.R
import com.magni.coursesdemo.feature.auth.AuthActivity
import com.magni.coursesdemo.feature.favorites.FavoritesFragment
import com.magni.coursesdemo.feature.home.presentation.HomeFragment
import com.magni.coursesdemo.feature.main.MainActivity
import com.magni.coursesdemo.feature.profile.ProfileFragment
import com.magni.coursesdemo.presentation.NavigationContract
import javax.inject.Inject

class AppNavigator @Inject constructor() : NavigationContract {
    override fun navigateToHome(context: Context) {
        if (context is MainActivity) {
            context.replaceFragment(HomeFragment())
            context.setSelectedBottomNavItem(R.id.nav_favorites)
        } else {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            context.startActivity(intent)
        }
    }

    override fun navigateToAuth(context: Context) {
        val intent = Intent(context, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    override fun navigateToFavorites(context: Context) {
        if (context is MainActivity) {
            context.replaceFragment(FavoritesFragment())
            context.setSelectedBottomNavItem(R.id.nav_favorites)
        } else {
            navigateToHome(context)
        }
    }

    override fun navigateToProfile(context: Context) {
        if (context is MainActivity) {
            context.replaceFragment(ProfileFragment())
            context.setSelectedBottomNavItem(R.id.nav_profile)
        } else {
            navigateToHome(context)
        }
    }
}