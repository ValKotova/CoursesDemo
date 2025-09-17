package com.magni.coursesdemo.di.navigation

import androidx.fragment.app.Fragment
import com.magni.coursesdemo.feature.favorites.FavoritesFragment
import com.magni.coursesdemo.feature.home.presentation.HomeFragment
import com.magni.coursesdemo.feature.profile.ProfileFragment
import com.magni.coursesdemo.presentation.FragmentContract
import javax.inject.Inject

class AppFragmentProvider @Inject constructor() : FragmentContract {
    override fun getHomeFragment(): Fragment {
        return HomeFragment()
    }

    override fun getFavoritesFragment(): Fragment {
        return FavoritesFragment()
    }

    override fun getProfileFragment(): Fragment {
        return ProfileFragment()
    }

}