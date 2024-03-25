package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemVehicleInsuranceCreditRatesBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.VehicleInsuranceCreditRatesLayoutBinding

class VehicleInsuranceCreditRatesCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: VehicleInsuranceCreditRatesLayoutBinding by lazy {
        VehicleInsuranceCreditRatesLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setVehicleInsuranceCreditRates(vehicleInsuranceCreditRates: VehicleInsuranceCreditRates) {
        binding.creditRatesLayout.removeAllViews()
        vehicleInsuranceCreditRates.forEach { vehicleInsuranceCreditRatesItem ->
            val mBinding: ItemVehicleInsuranceCreditRatesBinding = ItemVehicleInsuranceCreditRatesBinding.inflate(LayoutInflater.from(context))
            mBinding.tvCreditRateValue.text = vehicleInsuranceCreditRatesItem.insuranceValue
            mBinding.tvCreditMaxExpiry.text = vehicleInsuranceCreditRatesItem.maxExpiry
            mBinding.tvCreditMaxRate.text = vehicleInsuranceCreditRatesItem.maxRate

            binding.creditRatesLayout.addView(mBinding.root)
        }
    }
}