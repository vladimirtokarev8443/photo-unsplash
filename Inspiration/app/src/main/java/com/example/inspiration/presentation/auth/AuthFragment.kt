package com.example.inspiration.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.inspiration.R
import com.example.inspiration.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment: Fragment(R.layout.fragment_auth) {

    private val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)

    private val viewModel: AuthViewModel by viewModels()

    private lateinit var getAuthResponse: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGetAuthResponse()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
    }

    private fun initGetAuthResponse(){
        getAuthResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            viewModel.handleAuthResponseIntent(dataIntent)
        }
    }

    private fun bindViewModel() {
        binding.loginButton.setOnClickListener { viewModel.openLoginPage() }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.openAuthPageLiveData.observe(viewLifecycleOwner, ::openAuthPage)
        viewModel.toastLiveData.observe(viewLifecycleOwner, ::snackbar)
        viewModel.authSuccessLiveData.observe(viewLifecycleOwner) {
            //viewModel.saveVerificationStatus()
            findNavController().navigate(R.id.action_authFragment_to_tabsFragment)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        binding.loginButton.isVisible = !isLoading
        //binding.loginProgress.isVisible = isLoading
    }

    private fun openAuthPage(intent: Intent){
        getAuthResponse.launch(intent)
    }

    private fun snackbar(@StringRes resId: Int){
//        Snackbar.make(binding.authContainer, resId, Snackbar.LENGTH_INDEFINITE)
//            .setAction(getString(R.string.snackbar_action_text)){}
//            .show()
    }
}