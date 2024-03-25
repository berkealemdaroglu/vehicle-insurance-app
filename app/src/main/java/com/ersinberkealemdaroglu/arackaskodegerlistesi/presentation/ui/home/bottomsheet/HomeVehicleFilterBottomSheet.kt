package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home.bottomsheet

import android.view.View
import androidx.fragment.app.activityViewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeVehicleFilterBottomSheetBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseBottomSheet
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.SharedViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.doWhenResumed
import kotlinx.coroutines.flow.collectLatest

class HomeVehicleFilterBottomSheet(private val selectedVehicleFilterItem: SelectedVehicleFilterItem) :
    BaseBottomSheet<FragmentHomeVehicleFilterBottomSheetBinding>(FragmentHomeVehicleFilterBottomSheetBinding::inflate) {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: HomeVehicleFilterAdapter

    override fun initUI(view: View) {
        adapter = HomeVehicleFilterAdapter(selectedVehicleFilterItem) {
            viewModel.setSelectedVehicle(it)
            dismiss()
        }
        binding?.filterRV?.adapter = adapter

        doWhenResumed {
            viewModel.selectedFilterVehicle.collectLatest {
                if (it != null) {
                    adapter.setVehicleFilterItemList(it.filterBrandsList)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        bottomSheetBackgroundCornerRadius()
    }
}

enum class SelectedVehicleFilterItem {
    SELECTED_YEAR, SELECTED_BRAND, SELECTED_MODEL
}