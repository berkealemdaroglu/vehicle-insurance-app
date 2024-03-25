package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.detailList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModelItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemSearchScreenCarsBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL

class SearchListRecyclerViewAdapter : RecyclerView.Adapter<SearchListRecyclerViewAdapter.SearchListViewHolder>() {

    private val differ = AsyncListDiffer(
        this, diffCallback
    )

    var onItemClicked: ((CarDataResponseModelItem) -> Unit)? = null

    fun setData(items: List<CarDataResponseModelItem>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        return SearchListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user, onItemClicked)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class SearchListViewHolder(
        private val binding: ItemSearchScreenCarsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            carModel: CarDataResponseModelItem, onItemClicked: ((CarDataResponseModelItem) -> Unit)?
        ) {
            binding.apply {
                carModel.vehicleImages?.get(0)?.vehicleImage?.let { imgvBackground.loadImageFromURL(it) }
                val carInfo = carModel.vehicleTitle + " " + carModel.vehicleYear + " " + carModel.vehicleHp
                tvModel.text = carInfo

                root.setOnClickListener {
                    onItemClicked?.invoke(carModel)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): SearchListViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchScreenCarsBinding.inflate(inflater, parent, false)

                return SearchListViewHolder(binding)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CarDataResponseModelItem>() {
            override fun areItemsTheSame(
                oldItem: CarDataResponseModelItem, newItem: CarDataResponseModelItem
            ): Boolean {
                return oldItem.vehicleTitle == newItem.vehicleTitle
            }

            override fun areContentsTheSame(
                oldItem: CarDataResponseModelItem, newItem: CarDataResponseModelItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}