package com.magni.coursesdemo.presentation

import androidx.fragment.app.Fragment

interface FragmentContract {
    fun getHomeFragment(): Fragment
    fun getFavoritesFragment(): Fragment
    fun getProfileFragment(): Fragment
}