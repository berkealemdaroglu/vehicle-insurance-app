package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.creditcalculator

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentCreditCalculatorBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.CreditCalculator
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.afterTextChanged
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.afterTextChangedInsuranceFormat
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.afterTextChangedPriceFormat
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collapse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.doWhenResumed
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.expand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.fadeOutAndHide
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.formatPriceWithDotsForDecimal
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.gone
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.replaceDots
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.replaceMonth
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.seekBarChangeListener
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreditCalculatorFragment : BaseFragment<FragmentCreditCalculatorBinding, CreditCalculatorViewModel>(
    FragmentCreditCalculatorBinding::inflate
) {

    override val viewModel: CreditCalculatorViewModel by viewModels()
    private val args: CreditCalculatorFragmentArgs by navArgs()
    private val creditCalculator by lazy { CreditCalculator() }

    override fun initUI(view: View) {
        setupUI()
        creditCalculatorCollect()
        creditEdittextListeners()
        seekBarListener()
        againCreditCalculatorSetupUI()
        vehicleInsuranceCreditRatesCollect()
    }

    private fun setupUI() {
        binding?.apply {
            args.selectedVehicle.let { brand ->

                isLoanAvailableUI(brand.isLoanAvailable)

                selectedVehicleText.text = getString(R.string.format_vehicle_information, brand.vehicleTitle, brand.vehicleYear, brand.vehicleHp)
                if (brand.vehiclePrice?.contains(getText(R.string.text_currency)) == true) {
                    vehicleInsurancePrice.text = brand.vehiclePrice
                } else {
                    vehicleInsurancePrice.text = getString(R.string.text_with_currency, brand.vehiclePrice?.formatPriceWithDotsForDecimal())
                }

                maxCreditExpiry.text = brand.vehicleMaxCreditExpiry
                brand.vehicleLoanAmount?.let { loanAmount ->
                    maxCreditAmount.text = getString(R.string.text_with_currency, loanAmount.toString().formatPriceWithDotsForDecimal())
                    creditAmountEditText.setText(loanAmount.toString().formatPriceWithDotsForDecimal())
                    creditAmountSeekBar.max = loanAmount
                    creditAmountSeekBar.progress = loanAmount
                }

                brand.vehicleMaxCreditExpiry?.let { maxCreditExpiry ->
                    expireEdittext.setText(maxCreditExpiry)
                    expireDateSeekBar.max = maxCreditExpiry.replaceMonth().toInt()
                    expireDateSeekBar.progress = maxCreditExpiry.replaceMonth().toInt()
                }
                interestPercentEdittext.setText(getText(R.string.default_interest_percent))

                brand.vehicleLoanAmount?.toDouble()?.let { it1 ->
                    brand.vehicleMaxCreditExpiry?.replaceMonth()?.toIntOrNull()?.let { it2 ->
                        creditCalculator(it1, it2, getText(R.string.default_interest_percent).toString().toDoubleOrNull()!!)
                    }
                }
            }
        }

        toggleCollapseLayout()
    }

    private fun toggleCollapseLayout() {
        binding?.apply {
            expandLayout.setOnClickListener {
                if (collapseLayout.isVisible) {
                    expandIcon.rotation = 0f
                    collapseLayout.collapse()
                } else {
                    expandIcon.rotation = 90f
                    collapseLayout.expand(heightOffset = materialCardView.height * 2)
                }
            }
        }
    }

    private fun isLoanAvailableUI(isLoanAvailable: Boolean) {
        binding?.apply {
            if (!isLoanAvailable) {
                infoCreditTextView.visible()
                creditCalculatorButton.visible()
                expireEdittext.isEnabled = false
                creditAmountEditText.isEnabled = false
                interestPercentEdittext.isEnabled = false
            } else {
                infoCreditTextView.gone()
                creditCalculatorButton.gone()
                expireEdittext.isEnabled = true
                creditAmountEditText.isEnabled = true
                interestPercentEdittext.isEnabled = true
            }
        }
    }

    private fun againCreditCalculatorSetupUI() {
        binding?.apply {
            creditCalculatorButton.setOnClickListener {
                materialCardView.fadeOutAndHide()
                infoCreditTextView.fadeOutAndHide()
                creditCalculatorButton.fadeOutAndHide()
                creditAmountSeekBar.fadeOutAndHide()
                expireDateSeekBar.fadeOutAndHide()
                expireEdittext.isEnabled = true
                creditAmountEditText.isEnabled = true
                interestPercentEdittext.isEnabled = true
            }
        }
    }

    private fun creditEdittextListeners() {
        binding?.apply {
            creditAmountEditText.afterTextChangedPriceFormat {
                creditCalculator(
                    creditAmountEditText.text.toString().replaceDots().toDoubleOrNull() ?: 0.0,
                    expireEdittext.text.toString().replaceMonth().toIntOrNull() ?: 0,
                    interestPercentEdittext.text.toString().toDoubleOrNull() ?: 0.0
                )
            }

            expireEdittext.afterTextChanged {
                creditCalculator(
                    creditAmountEditText.text.toString().replaceDots().toDoubleOrNull() ?: 0.0,
                    expireEdittext.text.toString().replaceMonth().toIntOrNull() ?: 0,
                    interestPercentEdittext.text.toString().toDoubleOrNull() ?: 0.0
                )
            }

            interestPercentEdittext.afterTextChangedInsuranceFormat {
                creditCalculator(
                    creditAmountEditText.text.toString().replaceDots().toDoubleOrNull() ?: 0.0,
                    expireEdittext.text.toString().replaceMonth().toIntOrNull() ?: 0,
                    interestPercentEdittext.text.toString().toDoubleOrNull() ?: 0.0
                )
            }
        }
    }

    private fun seekBarListener() {
        binding?.apply {
            creditAmountSeekBar.seekBarChangeListener {
                creditAmountEditText.setText(it.toString().formatPriceWithDotsForDecimal())
            }

            expireDateSeekBar.seekBarChangeListener {
                expireEdittext.setText(it.toString())
            }
        }
    }

    private fun creditCalculator(creditBalance: Double, creditExpiry: Int, interestPercent: Double) {
        lifecycleScope.launch {
            creditCalculator.calculateAndPrintLoanDetails(creditBalance, creditExpiry, interestPercent)
        }
    }

    private fun creditCalculatorCollect() {
        lifecycleScope.launch {
            binding?.apply {
                creditCalculator.creditResult.collect { result ->
                    result?.let { creditModel ->
                        purchasePrice.text = getString(R.string.text_with_currency, creditModel.monthlyPayment)
                        totalPurchasePrice.text = getString(R.string.text_with_currency, creditModel.totalPayment)
                    }
                }
            }
        }
    }

    private fun vehicleInsuranceCreditRatesCollect() {
        binding?.apply {
            progressBar.visible()
            doWhenResumed {
                viewModel.getVehicleInsuranceCreditRates.collectLatest { result ->
                    result?.let { creditRates ->
                        vehicleInsuranceCreditRatesCustomView.setVehicleInsuranceCreditRates(creditRates)
                        progressBar.gone()
                        vehicleInsuranceCreditRatesCustomView.visible()
                    }
                }
            }
        }
    }

}