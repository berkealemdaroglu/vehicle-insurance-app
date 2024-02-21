package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeFragmentViewModel by viewModels()

    override fun initUI(view: View) {
        insureButtonHandle()
    }

    private fun insureButtonHandle() {
        binding?.apply {
            yearButton.setOnClickListener {
                viewModel.vehicleInsuranceMapper.filterByYear {
                    viewModel.setSelectedVehicle(yearList = it)
                    //openBottomSheet
                }
            }

            brandButton.setOnClickListener {
                viewModel.vehicleInsuranceMapper.filterByBrand(viewModel.getYear) {
                    viewModel.setSelectedVehicle(brandList = it)
                    //openBottomSheet
                }
            }

            modelButton.setOnClickListener {
                viewModel.vehicleInsuranceMapper.filterByYearAndBrand(viewModel.getYear, viewModel.getBrand) {
                    viewModel.setSelectedVehicle(brand = it)
                    //openBottomSheet
                }
            }
        }
    }
}