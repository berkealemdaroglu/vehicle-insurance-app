package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.detail

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentVehicleDetailBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemFeaturesDetailPageBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.doWhenResumed
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.formatPriceWithDotsForDecimal
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.invisible
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding, VehicleDetailViewModel>(FragmentVehicleDetailBinding::inflate) {

    override val viewModel: VehicleDetailViewModel by viewModels()
    private val navArgs: VehicleDetailFragmentArgs by navArgs()
    private val vehicleFeatureList: ArrayList<Pair<String, String?>> = ArrayList()

    override fun initUI(view: View) {
        setupUI()
        vehicleFeaturesAddView()
        navigateCreditCalculatePage()
        vehicleInsuranceCreditRatesCollect()
    }

    private fun setupUI() {
        navArgs.lowPriceCar.let { carData ->
            binding?.apply {
                carData.vehicleImages?.firstOrNull()?.vehicleImage?.let { mainImage.loadImageFromURL(it) }
                vehicleTitle.text = carData.vehicleTitle
                vehiclePriceText.text = carData.vehiclePrice
                creditInsurancePrice.text =
                    getString(R.string.text_with_currency, carData.vehicleLoanAmount.toString().formatPriceWithDotsForDecimal())
                vehicleFeatureList.add(Pair(getString(R.string.arac_yil), carData.vehicleYear))
                vehicleFeatureList.add(Pair(getString(R.string.marka), carData.vehicleBrand))
                vehicleFeatureList.add(Pair(getString(R.string.model), carData.vehicleModel))
                vehicleFeatureList.add(Pair(getString(R.string.motor_gucu), carData.vehicleHp))
            }
        }
    }

    private fun vehicleFeaturesAddView() {
        binding?.vehicleFeatureLayout?.removeAllViews()
        vehicleFeatureList.forEachIndexed { index, pair ->
            val featuresBinding = ItemFeaturesDetailPageBinding.inflate(layoutInflater)
            featuresBinding.featureTitle.text = pair.first
            featuresBinding.featureDesc.text = pair.second
            if (vehicleFeatureList.lastIndex == index) featuresBinding.featureLine.invisible()
            binding?.vehicleFeatureLayout?.addView(featuresBinding.root)
        }
    }

    private fun navigateCreditCalculatePage() {
        binding?.creditCalculatorButton?.setOnClickListener {
            val action = VehicleDetailFragmentDirections.actionVehicleDetailFragmentToCreditCalculatorFragment(navArgs.lowPriceCar)
            findNavController().navigate(action)
        }
    }

    private fun vehicleInsuranceCreditRatesCollect() {
        binding?.apply {
            progressBar.visible()

            doWhenResumed {
                viewModel.getVehicleInsuranceCreditRates.collectLatest { result ->
                    result?.let { creditRates ->
                        vehicleInsuranceCreditRatesCustomView.setVehicleInsuranceCreditRates(creditRates)
                        progressBar.isVisible = false
                        vehicleInsuranceCreditRatesCustomView.visible()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        vehicleFeatureList.clear()
    }
}