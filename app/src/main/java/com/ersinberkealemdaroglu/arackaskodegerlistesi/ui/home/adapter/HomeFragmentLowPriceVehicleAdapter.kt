package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemHomeFragmentCarsBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL

class HomeFragmentLowPriceVehicleAdapter : RecyclerView.Adapter<HomeFragmentLowPriceVehicleAdapter.LowPriceVehicleAdapterViewHolder>() {

    private lateinit var binding: ItemHomeFragmentCarsBinding
    private var carDataResponseModel: CarDataResponseModel = CarDataResponseModel()

    class LowPriceVehicleAdapterViewHolder(private val binding: ItemHomeFragmentCarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindVehicle(carDataResponseModelItem: CarDataResponseModel.CarDataResponseModelItem) {
            binding.apply {
                carDataResponseModelItem.vehicleImages?.firstOrNull()?.vehicleImage?.let { imgBackground.loadImageFromURL(it) }
                val carInfo =
                    carDataResponseModelItem.vehicleTitle + " " + carDataResponseModelItem.vehicleYear + " " + carDataResponseModelItem.vehicleHp
                tvModel.text = carInfo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LowPriceVehicleAdapterViewHolder {
        binding = ItemHomeFragmentCarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LowPriceVehicleAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = carDataResponseModel.size

    override fun onBindViewHolder(holder: LowPriceVehicleAdapterViewHolder, position: Int) {
        holder.bindVehicle(carDataResponseModel[position])
    }

    fun setCarDataModel(carDataResponseModelItem: CarDataResponseModel) {
        carDataResponseModel.clear()
        carDataResponseModel = carDataResponseModelItem
        notifyItemInserted(carDataResponseModel.size)
    }
}