package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detailList

import android.view.View
import androidx.fragment.app.viewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentVehicleSearchListBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleSearchListFragment :
    BaseFragment<FragmentVehicleSearchListBinding, VehicleSearchListViewModel>(FragmentVehicleSearchListBinding::inflate) {

    override val viewModel: VehicleSearchListViewModel by viewModels()

    override fun initUI(view: View) {
    }
}
