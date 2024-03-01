package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.splash

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentSplashBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.datastore.DataStoreManager
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.SharedViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.customviews.CustomDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SharedViewModel>(FragmentSplashBinding::inflate) {

    override val viewModel: SharedViewModel by activityViewModels()

    private val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager()
    }

    private lateinit var customDialogFragment: CustomDialogFragment

    override fun initUI(view: View) {
        //checkUpdate()

        lifecycleScope.launch {
            viewModel.splashLoading.collectLatest {
                if (it == true) {

                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }
        }
        errorHandling()
    }

    private fun errorHandling() {
        lifecycleScope.launch {
            val vehicleData = dataStoreManager.readVehicleData()

            viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                errorMessage?.let {
                    if (vehicleData.isNotEmpty()) {
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    } else {
                        openErrorPopup(it)
                    }
                }
            }
        }
    }

    private fun openErrorPopup(errorMessage: String?) {

        customDialogFragment = CustomDialogFragment().apply {
            onPositiveButtonClick = {
                viewModel.callRequests()
            }
            onNegativeButtonClick = {
                activity?.finish()
            }
            setTitleText = {
                "Hata"
            }
            setMessageText = {
                errorMessage
            }
            setPositiveButtonText = {
                "Tekrar Dene"
            }
            setNegativeButtonText = {
                "Kapat"
            }
        }

        if (customDialogFragment.isAdded) {
            return
        }
        customDialogFragment.show(parentFragmentManager, "customDialog")

    }
}