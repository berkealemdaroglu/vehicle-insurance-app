package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModelItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemHomeFragmentCarsBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL

class HomeFragmentLowPriceVehicleAdapter : RecyclerView.Adapter<HomeFragmentLowPriceVehicleAdapter.LowPriceVehicleAdapterViewHolder>() {

    private lateinit var binding: ItemHomeFragmentCarsBinding
    private var carDataResponseModel: CarDataResponseModel = CarDataResponseModel()
    var onItemClicked: ((CarDataResponseModelItem) -> Unit)? = null

    class LowPriceVehicleAdapterViewHolder(private val binding: ItemHomeFragmentCarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindVehicle(
            carDataResponseModelItem: CarDataResponseModelItem, onItemClicked: ((CarDataResponseModelItem) -> Unit)?
        ) {
            binding.apply {
                carDataResponseModelItem.vehicleImages?.firstOrNull()?.vehicleImage?.let { imgBackground.loadImageFromURL(it) }
                carDataResponseModelItem.let { model ->
                    tvModel.text = root.context.getString(R.string.format_vehicle_information, model.vehicleTitle, model.vehicleYear, model.vehicleHp)
                }

                root.rootView.setOnClickListener {
                    onItemClicked?.invoke(carDataResponseModelItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LowPriceVehicleAdapterViewHolder {
        binding = ItemHomeFragmentCarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LowPriceVehicleAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = carDataResponseModel.size

    override fun onBindViewHolder(holder: LowPriceVehicleAdapterViewHolder, position: Int) {
        holder.bindVehicle(carDataResponseModel[position], onItemClicked)
    }

    fun setCarDataModel(carDataResponseModelItem: CarDataResponseModel) {
        carDataResponseModel.clear()
        carDataResponseModel = carDataResponseModelItem
        notifyItemInserted(carDataResponseModel.size)
    }
}