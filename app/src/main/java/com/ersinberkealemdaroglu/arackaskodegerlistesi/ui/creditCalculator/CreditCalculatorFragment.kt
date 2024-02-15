package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.creditCalculator

import android.view.View
import androidx.fragment.app.viewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentCreditCalculatorBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCalculatorFragment : BaseFragment<FragmentCreditCalculatorBinding, CreditCalculatorViewModel>(FragmentCreditCalculatorBinding::inflate) {

    override val viewModel: CreditCalculatorViewModel by viewModels()

    override fun initUI(view: View) {

    }
}