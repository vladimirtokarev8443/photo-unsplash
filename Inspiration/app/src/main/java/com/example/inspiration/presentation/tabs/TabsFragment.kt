package com.example.inspiration.presentation.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.inspiration.R
import com.example.inspiration.databinding.FragmentTabsBinding

class TabsFragment: Fragment(R.layout.fragment_tabs) {

    private val binding: FragmentTabsBinding by viewBinding(FragmentTabsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.photo_nav_graph,
                R.id.collection_nav_graph,
                R.id.user_nav_graph
            )
        )

        binding.bottomNavigationView.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

}