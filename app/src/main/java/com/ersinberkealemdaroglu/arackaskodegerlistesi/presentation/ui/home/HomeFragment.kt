package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModelItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.SharedViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home.adapter.HomeFragmentBlogAdapter
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home.adapter.HomeFragmentLowPriceVehicleAdapter
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home.bottomsheet.SelectedVehicleFilterItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collapse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.doWhenResumed
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.expand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.formatPriceWithDotsForDecimal
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.gone
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.vehicleLoanAmountCalculator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, SharedViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: SharedViewModel by activityViewModels()
    private val homeBlogAdapter: HomeFragmentBlogAdapter = HomeFragmentBlogAdapter()

    override fun initUI(view: View) {
        insureButtonHandle()
        selectedVehicleState()
        setRecyclerView()
    }

    private fun insureButtonHandle() {
        binding?.apply {
            yearButton.setOnClickListener {
                viewModel.vehicleInsuranceFilter.filterByYear {
                    viewModel.setSelectedFilter(it)
                    openVehicleFilterBottomSheet(selectedVehicleFilterItem = SelectedVehicleFilterItem.SELECTED_YEAR)
                }
            }

            brandButton.setOnClickListener {
                viewModel.vehicleInsuranceFilter.filterByBrand(viewModel.getYear) {
                    viewModel.setSelectedFilter(brandList = it)
                    openVehicleFilterBottomSheet(SelectedVehicleFilterItem.SELECTED_BRAND)
                }
            }

            modelButton.setOnClickListener {
                viewModel.vehicleInsuranceFilter.filterByYearAndBrand(viewModel.getYear, viewModel.getBrand) { response ->
                    val sortedResponse = response?.sortedBy { it.vehicleModels?.firstOrNull()?.modelName }
                    viewModel.setSelectedFilter(brandList = sortedResponse)
                    openVehicleFilterBottomSheet(SelectedVehicleFilterItem.SELECTED_MODEL)
                }
            }
        }
    }

    private fun selectedVehicleState() {
        viewLifecycleOwner.lifecycleScope.launch {

            doWhenResumed {
                viewModel.selectedVehicle.collectLatest { brand ->
                    if (brand != null) {
                        binding?.apply {
                            when (brand.isSelectedVehicle) {
                                SelectedVehicleFilterItem.SELECTED_YEAR -> selectYearButtonUI(brand)

                                SelectedVehicleFilterItem.SELECTED_BRAND -> selectBrandButtonUI(brand)

                                SelectedVehicleFilterItem.SELECTED_MODEL -> selectModelButtonUI(brand)

                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectYearButtonUI(brand: Brand) {
        binding?.apply {
            brand.vehicleYear?.let { viewModel.setVehicleYear(it) }
            yearButton.text = brand.vehicleYear
            brandButton.isEnabled = true
            brandButton.text = getText(R.string.marka)
            modelButton.text = getText(R.string.model)
            modelButton.isEnabled = false
            insuranceVehicleLayout.collapse()
        }
    }

    private fun selectBrandButtonUI(brand: Brand) {
        binding?.apply {
            viewModel.setVehicleBrand(brand.brandName)

            brandButton.text = brand.brandName
            modelButton.text = getText(R.string.model)
            modelButton.isEnabled = true
            insuranceVehicleLayout.collapse()
        }
    }

    private fun selectModelButtonUI(brand: Brand) {
        binding?.apply {
            val vehicleTitleStr = brand.brandName + " " + brand.vehicleModels?.firstOrNull()?.modelName
            vehicleTitle.text = vehicleTitleStr
            yearButton.text = brand.vehicleYear
            yearButton.isEnabled = true
            brandButton.text = brand.brandName
            brandButton.isEnabled = true
            vehiclePrice.text =
                getString(R.string.home_page_kasko_degeri, brand.vehicleModels?.firstOrNull()?.price.toString().formatPriceWithDotsForDecimal())
            modelButton.text = brand.vehicleModels?.firstOrNull()?.modelName
            modelButton.isEnabled = true

            if (!insuranceVehicleLayout.isVisible) insuranceVehicleLayout.expand()

            //Buraya bakılacak.
            binding?.creditCalculatorButton?.setOnClickListener {
                val carInfo = CarDataResponseModelItem(
                    vehicleTitle = vehicleTitleStr,
                    vehiclePrice = brand.vehicleModels?.firstOrNull()?.price.toString(),
                    vehicleYear = viewModel.getYear,
                    vehicleBrand = brand.brandName,
                    vehicleModel = brand.vehicleModels?.firstOrNull()?.modelName,
                    vehicleMaxCreditExpiry = brand.vehicleModels?.firstOrNull()?.price?.vehicleLoanAmountCalculator(requireContext())?.vehicleMaxCreditExpiry,
                    vehicleLoanAmount = brand.vehicleModels?.firstOrNull()?.price?.vehicleLoanAmountCalculator(requireContext())?.vehicleLoanAmount?.toInt(),
                    isLoanAvailable = brand.vehicleModels?.firstOrNull()?.price?.vehicleLoanAmountCalculator(requireContext())?.isLoanAvailable ?: false
                )

                val action = HomeFragmentDirections.actionHomeFragmentToCreditCalculatorFragment(carInfo)
                findNavController().navigate(action)
            }
        }
    }

    private fun setRecyclerView() {
        homeBlogAdapter.onItemClickCallback = { vehicleBlogItem ->
            val action = HomeFragmentDirections.actionHomeFragmentToBlogFragment(vehicleBlogItem)
            findNavController().navigate(action)
        }
        binding?.blogRV?.adapter = homeBlogAdapter
        binding?.blogRV?.setHasFixedSize(true)

        vehicleBlogObserve()
    }

    private fun vehicleBlogObserve() {
        if (viewModel.getIsBlogVisible == true) {
            binding?.blogTextLayout?.gone()
            binding?.blogRV?.gone()
            return
        }

        doWhenResumed {
            viewModel.getVehicleBlogData.collectLatest { vehicleBlog ->
                if (vehicleBlog != null) {
                    homeBlogAdapter.setVehicleBlog(vehicleBlog)
                }
            }
        }

    }
}