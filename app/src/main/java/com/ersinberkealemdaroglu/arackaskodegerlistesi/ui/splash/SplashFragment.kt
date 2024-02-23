package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.splash

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentSplashBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, HomeFragmentViewModel>(FragmentSplashBinding::inflate) {

    override val viewModel: HomeFragmentViewModel by activityViewModels()

    override fun initUI(view: View) {

        lifecycleScope.launch {
            viewModel.splashLoading.collectLatest {
                if (it == true) {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }
        }
    }
}