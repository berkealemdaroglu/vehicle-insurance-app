package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.favorites

import android.view.View
import androidx.fragment.app.viewModels
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentFavoritiesVehiclesBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesVehiclesFragment :
    BaseFragment<FragmentFavoritiesVehiclesBinding, FavoritesVehiclesViewModel>(FragmentFavoritiesVehiclesBinding::inflate) {

    override val viewModel: FavoritesVehiclesViewModel by viewModels()

    override fun initUI(view: View) {

    }
}