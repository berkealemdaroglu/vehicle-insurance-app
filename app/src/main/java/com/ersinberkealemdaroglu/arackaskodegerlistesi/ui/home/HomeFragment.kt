package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModelItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.datastore.DataStoreManager
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.adapter.HomeFragmentBlogAdapter
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.adapter.HomeFragmentLowPriceVehicleAdapter
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomsheet.SelectedVehicleFilterItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collapse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collectWhenStarted
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.expand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.formatPriceWithDotsForDecimal
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.gone
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.vehicleLoanAmountCalculator
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, SharedViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: SharedViewModel by activityViewModels()
    private lateinit var homeFragmentLowPriceVehicleAdapter: HomeFragmentLowPriceVehicleAdapter
    private val homeBlogAdapter: HomeFragmentBlogAdapter = HomeFragmentBlogAdapter()
    private val dataStoreManager = DataStoreManager()

    override fun initUI(view: View) {
        viewModel.getVehicleBlog()

        insureButtonHandle()
        selectedVehicleState()
        getLowPriceVehicles()
        openCreditCalculatorFragment()
        vehicleBlogObserve()
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
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.selectedVehicle.collectWhenStarted(viewLifecycleOwner) { brand ->
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
        }
    }

    private fun selectBrandButtonUI(brand: Brand) {
        binding?.apply {
            viewModel.setVehicleBrand(brand.brandName)
            brandButton.text = brand.brandName
            modelButton.text = MODEL_BUTTON_TEXT
            modelButton.isEnabled = true
            insuranceVehicleLayout.collapse()
        }
    }

    private fun selectModelButtonUI(brand: Brand) {
        binding?.apply {
            val vehicleTitleStr = brand.brandName + " " + brand.vehicleModels?.firstOrNull()?.modelName
            vehicleTitle.text = vehicleTitleStr
            yearButton.text = brand.years
            yearButton.isEnabled = true
            brandButton.text = brand.brandName
            brandButton.isEnabled = true
            vehiclePrice.text =
                getString(R.string.kasko_degeri) + brand.vehicleModels?.firstOrNull()?.price.toString().formatPriceWithDotsForDecimal() + " ₺"
            modelButton.text = brand.vehicleModels?.firstOrNull()?.modelName
            modelButton.isEnabled = true

            if (!insuranceVehicleLayout.isVisible) insuranceVehicleLayout.expand()

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

            binding?.creditCalculatorButton?.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCreditCalculatorFragment(carInfo)
                findNavController().navigate(action)
            }
        }
    }

    private fun getLowPriceVehicles() {
        homeFragmentLowPriceVehicleAdapter = HomeFragmentLowPriceVehicleAdapter()
        binding?.lowPriceVehicleRV?.adapter = homeFragmentLowPriceVehicleAdapter

        lifecycleScope.launch {
            val lowVehicleData = dataStoreManager.readLowPriceVehicleData()
            if (lowVehicleData.isNotEmpty()) {
                val gson = Gson()
                val vehicleData = gson.fromJson(lowVehicleData, CarDataResponseModel::class.java)
                homeFragmentLowPriceVehicleAdapter.setCarDataModel(vehicleData)
                openCarSearchFragment(vehicleData)
            }

        }
    }

    private fun openCreditCalculatorFragment() {
        homeFragmentLowPriceVehicleAdapter.onItemClicked = { carData ->
            val action = HomeFragmentDirections.actionHomeFragmentToVehicleDetailFragment(
                carData
            )
            findNavController().navigate(action)
        }

    }

    private fun openCarSearchFragment(carDataResponseModel: CarDataResponseModel) {
        binding?.lowPriceVehicleBtnGoSearch?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToVehicleSearchListFragment(
                carDataResponseModel
            )
            findNavController().navigate(action)
        }
    }

    private fun vehicleBlogObserve() {
        binding?.apply {
            if (viewModel.getIsBlogVisible == true) {
                blogTextLayout.gone()
                blogRV.gone()
            } else {
                viewModel.getVehicleBlogData.collectWhenStarted(viewLifecycleOwner) { vehicleBlog ->
                    if (vehicleBlog != null) {
                        homeBlogAdapter.setVehicleBlog(vehicleBlog)
                        blogRV.adapter = homeBlogAdapter
                    }
                }
            }
            homeBlogAdapter.onItemClickCallback = { vehicleBlogItem ->
                val action = HomeFragmentDirections.actionHomeFragmentToBlogFragment(vehicleBlogItem)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        private const val BRAND_BUTTON_TEXT = "Marka"
        private const val MODEL_BUTTON_TEXT = "Model"
    }
}