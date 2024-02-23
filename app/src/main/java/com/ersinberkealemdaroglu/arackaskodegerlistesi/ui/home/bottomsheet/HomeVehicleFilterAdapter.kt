package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.VehicleFilterItemBinding

class HomeVehicleFilterAdapter(
    private val selectedVehicleFilterItem: SelectedVehicleFilterItem, private val selectedBrandItem: ((brand: Brand) -> Unit)
) : RecyclerView.Adapter<HomeVehicleFilterAdapter.VehicleFilterViewHolder>() {

    private lateinit var binding: VehicleFilterItemBinding
    private lateinit var brandList: List<Brand>

    class VehicleFilterViewHolder(private val binding: VehicleFilterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindVehicleItem(
            brand: Brand?, selectedVehicleFilterItem: SelectedVehicleFilterItem, selectedBrandItem: ((brand: Brand) -> Unit)
        ) {
            binding.apply {
                brand?.let { brandItem ->

                    filterItemText.text = when (selectedVehicleFilterItem) {
                        SelectedVehicleFilterItem.SELECTED_YEAR -> {
                            brandItem.years
                        }

                        SelectedVehicleFilterItem.SELECTED_BRAND -> {
                            brandItem.brandName
                        }

                        SelectedVehicleFilterItem.SELECTED_MODEL -> {
                            brandItem.vehicleModels?.joinToString(separator = ", ") { it?.modelName.orEmpty() }.orEmpty()
                        }
                    }

                    filterItemText.setOnClickListener {
                        selectedBrandItem(Brand(brandItem.brandName, brandItem.years, brandItem.vehicleModels, selectedVehicleFilterItem))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleFilterViewHolder {
        binding = VehicleFilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleFilterViewHolder, position: Int) {
        holder.bindVehicleItem(brandList[position], selectedVehicleFilterItem, selectedBrandItem)
    }

    override fun getItemCount(): Int = brandList.size

    fun setVehicleFilterItemList(brandList: List<Brand>) {
        this.brandList = brandList
        notifyItemInserted(brandList.size)
    }
}