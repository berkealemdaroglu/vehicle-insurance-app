package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detailList

import android.view.LayoutInflater
import android.view.View
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModelItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.BottomSheetFilterBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemFilterBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseBottomSheet
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.gone

class FilterBottomSheet(private val carData: CarDataResponseModel) : BaseBottomSheet<BottomSheetFilterBinding>(BottomSheetFilterBinding::inflate) {

    private var selectedFilterIndex = -1
    var onItemClicked: ((List<CarDataResponseModelItem>) -> Unit)? = null

    companion object {
        var selectedIndex: Int = -1
    }

    override fun initUI(view: View) {
        val filterList = arrayListOf(
            "Fiyata Göre (Önce en yüksek)", "Fiyata Göre (Önce en düşük)", "Yıla göre (Önce en yeni araçlar)", "Yıla göre (Önce en eski araçlar)"

        )

        filterList.forEachIndexed { index, filter ->
            val itemBinding = ItemFilterBinding.inflate(
                LayoutInflater.from(view.context), binding?.linearLayout, false
            )
            itemBinding.itemText.text = filter

            if (selectedIndex != -1 && index == selectedIndex) {
                itemBinding.itemIcon.setImageResource(R.drawable.ic_filter_active)
            } else {
                itemBinding.itemIcon.setImageResource(R.drawable.ic_filter_passive)
            }

            if (index == 3) {
                itemBinding.divider.gone()
            }

            itemBinding.root.setOnClickListener {
                selectedFilterIndex = index
                updateIcons()
            }

            binding?.linearLayout?.addView(itemBinding.root)
        }
    }

    private fun updateIcons() {
        // LinearLayout içindeki tüm çocuklar için döngü
        for (i in 0 until (binding?.linearLayout?.childCount ?: 0)) {
            // Doğru dönüşüm için önce çocuk görünümü al
            val childView = binding?.linearLayout?.getChildAt(i)

            // 'childView' üzerinden ItemFilterBinding örneğini elde et
            childView?.let { child ->
                val itemBinding = ItemFilterBinding.bind(child)

                // Seçili indekse göre ikonu güncelle
                if (i == selectedFilterIndex) {
                    itemBinding.itemIcon.setImageResource(R.drawable.ic_filter_active)
                    when (selectedFilterIndex) {
                        0 -> {
                            // Fiyata göre (Önce en yüksek)
                            val sortByDescPrice = carData.sortedByDescending { it.vehiclePrice }
                            onItemClicked?.invoke(sortByDescPrice)
                            selectedIndex = 0
                        }

                        1 -> {
                            // Fiyata göre (Önce en düşük)
                            val sortedByAscPrice = carData.sortedBy { it.vehiclePrice }
                            onItemClicked?.invoke(sortedByAscPrice)
                            selectedIndex = 1
                        }

                        2 -> {
                            // Yıla göre (Önce en yeni araçlar)
                            val sortByDescYear = carData.sortedByDescending { it.vehicleYear }
                            onItemClicked?.invoke(sortByDescYear)
                            selectedIndex = 2
                        }

                        3 -> {
                            // Yıla göre (Önce en eski araçlar)
                            val sortedByAscYear = carData.sortedBy { it.vehicleYear }
                            onItemClicked?.invoke(sortedByAscYear)
                            selectedIndex = 3
                        }
                    }
                    dismiss()
                } else {
                    itemBinding.itemIcon.setImageResource(R.drawable.ic_filter_passive)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBackgroundCornerRadius()
    }
}