package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.detailList

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentVehicleSearchListBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.SharedViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleSearchListFragment : BaseFragment<FragmentVehicleSearchListBinding, SharedViewModel>(
    FragmentVehicleSearchListBinding::inflate
) {

    override val viewModel: SharedViewModel by viewModels()
    private val args: VehicleSearchListFragmentArgs by navArgs()
    private val adapter = SearchListRecyclerViewAdapter()

    override fun initUI(view: View) {
        setRecyclerView(args.lowPriceCars)
        setSearchView()
        openCreditCalculatorFragment()
    }

    private fun setRecyclerView(carModel: CarDataResponseModel) {
        binding?.apply {
            recyclerCarsList.layoutManager = LinearLayoutManager(requireContext())
            adapter.setData(carModel)
            recyclerCarsList.adapter = adapter
        }
    }

    private fun setSearchView() {
        binding?.apply {
            searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterCarList(newText)
                    return true
                }
            })
        }
    }

    private fun filterCarList(queryText: String?) {
        val filteredCars = if (queryText.isNullOrEmpty()) {
            args.lowPriceCars
        } else {
            args.lowPriceCars.filter { car ->
                car.vehicleTitle?.lowercase()?.contains(queryText.lowercase()) == true
            }
        }
        adapter.setData(filteredCars)
        binding?.recyclerCarsList?.smoothScrollToPosition(0)
    }

    private fun openCreditCalculatorFragment() {
        adapter.onItemClicked = { carData ->
            val action = VehicleSearchListFragmentDirections.actionVehicleSearchListFragmentToVehicleDetailFragment(carData)
            findNavController().navigate(action)
        }
    }

    fun openFilterBottomSheet() {
        val filterBottomSheet = FilterBottomSheet(args.lowPriceCars)
        filterBottomSheet.show(childFragmentManager, filterBottomSheet.tag)

        filterBottomSheet.onItemClicked = {
            adapter.setData(it)
        }
    }
}
