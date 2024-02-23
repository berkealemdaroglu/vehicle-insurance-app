package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomSheet

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentHomeVehicleFilterBottomSheetBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseBottomSheet
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.HomeFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeVehicleFilterBottomSheet(private val selectedVehicleFilterItem: SelectedVehicleFilterItem) :
    BaseBottomSheet<FragmentHomeVehicleFilterBottomSheetBinding>(FragmentHomeVehicleFilterBottomSheetBinding::inflate) {

    private val viewModel: HomeFragmentViewModel by activityViewModels()
    private lateinit var adapter: HomeVehicleFilterAdapter

    override fun initUI(view: View) {
        adapter = HomeVehicleFilterAdapter(selectedVehicleFilterItem) {
            viewModel.setSelectedVehicle(it)
            dismiss()
        }
        binding?.filterRV?.adapter = adapter

        lifecycleScope.launch {
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