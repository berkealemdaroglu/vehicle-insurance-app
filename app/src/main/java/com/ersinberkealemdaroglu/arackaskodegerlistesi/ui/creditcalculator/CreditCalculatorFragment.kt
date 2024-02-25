package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.creditcalculator

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentCreditCalculatorBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCalculatorFragment :
    BaseFragment<FragmentCreditCalculatorBinding, CreditCalculatorViewModel>(
        FragmentCreditCalculatorBinding::inflate
    ) {

    override val viewModel: CreditCalculatorViewModel by viewModels()
    private val args: CreditCalculatorFragmentArgs by navArgs()

    override fun initUI(view: View) {
        val carInfo = args.selectedVehicle
        println("SEYMEN DATA calculator geldi carInfo =$carInfo ")

    }
}