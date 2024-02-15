package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collectWhenPrimitiveTypeStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeFragmentViewModel by viewModels()

    override fun initUI(view: View) {
        viewModel.getVehicle()

        viewModel.vehicleInsuranceResponse.collectWhenPrimitiveTypeStarted(viewLifecycleOwner) {
            println(it)
        }
    }
}