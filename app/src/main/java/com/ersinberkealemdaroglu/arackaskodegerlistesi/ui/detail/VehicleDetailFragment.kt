package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detail

import android.view.View
import androidx.fragment.app.viewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentVehicleDetailBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding, VehicleDetailViewModel>(FragmentVehicleDetailBinding::inflate) {

    override val viewModel: VehicleDetailViewModel by viewModels()

    override fun initUI(view: View) {

    }
}