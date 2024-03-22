package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentVehicleDetailBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemFeaturesDetailPageBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.formatPriceWithDotsForDecimal
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.invisible
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding, VehicleDetailViewModel>(FragmentVehicleDetailBinding::inflate) {

    override val viewModel: VehicleDetailViewModel by viewModels()
    private val navArgs: VehicleDetailFragmentArgs by navArgs()
    private val vehicleFeatureList: ArrayList<Pair<String, String?>> = ArrayList()
    private val carData by lazy { navArgs.lowPriceCar }

    override fun initUI(view: View) {
        setupUI()
        vehicleFeaturesAddView()
        navigateCreditCalculatePage()
        vehicleInsuranceCreditRatesCollect()
    }

    private fun setupUI() {
        carData.let { carData ->
            binding?.apply {
                carData.vehicleImages?.firstOrNull()?.vehicleImage?.let { mainImage.loadImageFromURL(it) }
                vehicleTitle.text = carData.vehicleTitle
                vehiclePriceText.text = carData.vehiclePrice
                creditInsurancePrice.text = carData.vehicleLoanAmount.toString().formatPriceWithDotsForDecimal() + CURRENCY
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
            val action = VehicleDetailFragmentDirections.actionVehicleDetailFragmentToCreditCalculatorFragment(carData)
            findNavController().navigate(action)
        }
    }

    private fun vehicleInsuranceCreditRatesCollect() {
        viewModel.getVehicleInsuranceCreditRates()
        binding?.apply {
            lifecycleScope.launch {
                viewModel.getVehicleInsuranceCreditRates.collectLatest { result ->
                    result?.let { creditRates ->
                        binding?.vehicleInsuranceCreditRatesCustomView?.setVehicleInsuranceCreditRates(creditRates)
                    }
                }
            }
        }
    }

    companion object {
        private const val CURRENCY = " ₺"
    }
}