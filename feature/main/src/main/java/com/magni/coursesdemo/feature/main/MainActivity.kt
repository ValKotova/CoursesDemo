package com.magni.coursesdemo.feature.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.magni.coursesdemo.presentation.R
import com.magni.coursesdemo.feature.main.databinding.ActivityMainBinding
import com.magni.coursesdemo.presentation.FragmentContract
import com.magni.coursesdemo.presentation.NavigationContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentNavItem: Int = R.id.nav_home

    @Inject
    lateinit var navigator: NavigationContract

    @Inject
    lateinit var fragmentProvider: FragmentContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        showInitialFragment()
        setupBackPressHandler()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(fragmentProvider.getHomeFragment())
                    currentNavItem = R.id.nav_home
                    true
                }
                R.id.nav_favorites -> {
                    replaceFragment(fragmentProvider.getFavoritesFragment())
                    currentNavItem = R.id.nav_favorites
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(fragmentProvider.getProfileFragment())
                    currentNavItem = R.id.nav_profile
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.selectedItemId = currentNavItem
    }

    private fun showInitialFragment() {
        replaceFragment(fragmentProvider.getHomeFragment())
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(com.magni.coursesdemo.feature.main.R.id.fragment_container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    fun setSelectedBottomNavItem(itemId: Int) {
        binding.bottomNavigation.selectedItemId = itemId
        currentNavItem = itemId
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    binding.bottomNavigation.selectedItemId = currentNavItem
                } else {
                    finish()
                }
            }
        })
    }
}