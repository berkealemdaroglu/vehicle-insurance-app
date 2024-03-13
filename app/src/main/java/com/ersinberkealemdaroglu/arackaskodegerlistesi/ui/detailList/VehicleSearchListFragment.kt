package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detailList

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentVehicleSearchListBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleSearchListFragment : BaseFragment<FragmentVehicleSearchListBinding, VehicleSearchListViewModel>(
    FragmentVehicleSearchListBinding::inflate
) {

    override val viewModel: VehicleSearchListViewModel by viewModels()
    private val args: VehicleSearchListFragmentArgs by navArgs()
    private val cars by lazy { args.lowPriceCars }
    private val adapter = SearchListRecyclerViewAdapter()

    override fun initUI(view: View) {
        setRecyclerView(cars)
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
            cars
        } else {
            cars.filter { car ->
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
        val filterBottomSheet = FilterBottomSheet(cars)
        filterBottomSheet.show(childFragmentManager, filterBottomSheet.tag)

        filterBottomSheet.onItemClicked = {
            adapter.setData(it)
        }
    }
}
