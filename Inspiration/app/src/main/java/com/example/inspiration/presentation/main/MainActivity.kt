package com.example.inspiration.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.domain.enum.VerificationStatus
import com.example.inspiration.R
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

        hideSystemBottomNav(findViewById<ConstraintLayout>(R.id.fragmentContainerView))

    }

    private fun hideSystemBottomNav(view: View){
        WindowCompat.getInsetsController(window, view).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    private fun observeViewModel(){

        viewModel.verificationStateFlow
            .onEach {

                it?.let { verificationStatus ->

                   val destIdRes = getDestinationId(status = VerificationStatus.valueOf(verificationStatus))

                    setStartDestination(destIdRes = destIdRes)
                }
            }
            .launchIn(lifecycleScope)

    }

    private fun getDestinationId(status: VerificationStatus): Int {
        Timber.d(status.name)
        return when(status){
            VerificationStatus.FIRST_VIZIT -> {R.id.onboardingFragment}
            VerificationStatus.NOT_VERIFIED -> {R.id.authFragment}
            else -> {R.id.tabsFragment}
        }
    }

    private fun setStartDestination(@IdRes destIdRes: Int) {

        navGraph.setStartDestination(destIdRes)

        navController.graph = navGraph
    }

}