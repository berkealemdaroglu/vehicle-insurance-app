package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomSheet.SelectedVehicleFilterItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collapse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.expand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeFragmentViewModel by activityViewModels()

    override fun initUI(view: View) {
        insureButtonHandle()
        selectedVehicleState()
    }

    private fun insureButtonHandle() {
        binding?.apply {
            yearButton.setOnClickListener {
                viewModel.vehicleInsuranceMapper.filterByYear {
                    viewModel.setSelectedFilter(it)
                    openVehicleFilterBottomSheet(selectedVehicleFilterItem = SelectedVehicleFilterItem.SELECTED_YEAR)
                }
            }

            brandButton.setOnClickListener {
                viewModel.vehicleInsuranceMapper.filterByBrand(viewModel.getYear) {
                    viewModel.setSelectedFilter(brandList = it)
                    openVehicleFilterBottomSheet(SelectedVehicleFilterItem.SELECTED_BRAND)
                }
            }

            modelButton.setOnClickListener {
                viewModel.vehicleInsuranceMapper.filterByYearAndBrand(viewModel.getYear, viewModel.getBrand) { response ->
                    viewModel.setSelectedFilter(brandList = response)
                    openVehicleFilterBottomSheet(SelectedVehicleFilterItem.SELECTED_MODEL)
                }
            }
        }
    }

    private fun selectedVehicleState() {
        lifecycleScope.launch {
            viewModel.selectedVehicle.collectLatest { brand ->
                if (brand != null) {
                    binding?.apply {
                        when (brand.isSelectedVehicle) {
                            SelectedVehicleFilterItem.SELECTED_YEAR -> {
                                selectYearButtonUI(brand)
                            }

                            SelectedVehicleFilterItem.SELECTED_BRAND -> {
                                selectBrandButtonUI(brand)
                            }

                            SelectedVehicleFilterItem.SELECTED_MODEL -> {
                                selectModelButtonUI(brand)
                            }

                            null -> {}
                        }
                    }
                }
            }
        }
    }

    private fun selectYearButtonUI(brand: Brand) {
        binding?.apply {
            brand.years?.let { viewModel.setVehicleYear(it) }
            yearButton.text = brand.years
            brandButton.isEnabled = true
            brandButton.text = BRAND_BUTTON_TEXT
            modelButton.text = MODEL_BUTTON_TEXT
            modelButton.isEnabled = false
            insuranceVehicleLayout.collapse()
            // açık olan kasko bedel layoutu kapat
        }
    }

    private fun selectBrandButtonUI(brand: Brand) {
        binding?.apply {
            viewModel.setVehicleBrand(brand.brandName)
            brandButton.text = brand.brandName
            modelButton.text = MODEL_BUTTON_TEXT
            modelButton.isEnabled = true
            insuranceVehicleLayout.collapse()
            // açık olan kasko bedel layoutu kapat
        }
    }

    private fun selectModelButtonUI(brand: Brand) {
        binding?.apply {
            // motionlayout ile kasko layoutunu visible değilse yap.
            vehicleTitle.text = brand.brandName + " " + brand.vehicleModels?.firstOrNull()?.modelName
            vehiclePrice.text = brand.vehicleModels?.firstOrNull()?.price.toString()
            modelButton.text = brand.vehicleModels?.firstOrNull()?.modelName

            if (!insuranceVehicleLayout.isVisible) insuranceVehicleLayout.expand()

            binding?.creditCalculatorButton?.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCreditCalculatorFragment(brand)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        private const val BRAND_BUTTON_TEXT = "Marka"
        private const val MODEL_BUTTON_TEXT = "Model"
    }
}