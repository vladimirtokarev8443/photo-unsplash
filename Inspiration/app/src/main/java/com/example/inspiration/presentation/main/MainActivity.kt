package com.example.inspiration.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.inspiration.R
import com.example.inspiration.data.enum.Verification
import com.example.inspiration.data.network.auth.AccessToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHost.navController

        navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        observeViewModel()

    }

    private fun observeViewModel(){

        viewModel.verificationStateFlow
            .onEach {

                it?.let { userVerification ->

                   val destIdRes = getDestinationId(verification = userVerification.verificationValue)

                    setStartDestination(destIdRes = destIdRes)
                }
            }
            .launchIn(lifecycleScope)

    }

    private fun getDestinationId(verification: Verification): Int {
        Timber.d(verification.name)
        return when(verification){
            Verification.FIRST_VIZIT -> {R.id.onboardingFragment}
            Verification.NOT_VERIFIED -> {R.id.authFragment}
            else -> {R.id.tabsFragment}
        }
    }

    private fun setStartDestination(@IdRes destIdRes: Int) {

        navGraph.setStartDestination(destIdRes)

        navController.graph = navGraph
    }

}