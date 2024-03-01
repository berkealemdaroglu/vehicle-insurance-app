package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.creditcalculator

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentCreditCalculatorBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.CreditCalculator
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.collapse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.expand
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCalculatorFragment : BaseFragment<FragmentCreditCalculatorBinding, CreditCalculatorViewModel>(
    FragmentCreditCalculatorBinding::inflate
) {

    override val viewModel: CreditCalculatorViewModel by viewModels()
    private val args: CreditCalculatorFragmentArgs by navArgs()
    val vehicleModel by lazy { args.selectedVehicle }
    private val creditCalculator by lazy { CreditCalculator() }

    override fun initUI(view: View) {

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
}