package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.blog

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.FragmentBlogBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.SharedViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL

class BlogFragment : BaseFragment<FragmentBlogBinding, SharedViewModel>(FragmentBlogBinding::inflate) {

    override val viewModel: SharedViewModel by activityViewModels()
    private val navArgs by navArgs<BlogFragmentArgs>()
    private val blogResponse by lazy { navArgs.VehicleBlogResponseItem }

    override fun initUI(view: View) {
        viewModel.getVehicleBlog()
        vehicleBlogObserve()
    }

    private fun vehicleBlogObserve() {
        blogResponse.let {
            binding?.apply {
                it.blogImage?.let { it1 -> blogItemImageView.loadImageFromURL(it1) }
                blogItemTitleTextView.text = it.blogTitle
                blogItemContentTextView.text = it.blogDescription
            }
        }
    }
}