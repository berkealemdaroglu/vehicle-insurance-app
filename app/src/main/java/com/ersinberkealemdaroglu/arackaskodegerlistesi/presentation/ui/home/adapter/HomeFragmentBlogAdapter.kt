package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponseItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemBlogRvBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL

class HomeFragmentBlogAdapter :
    RecyclerView.Adapter<HomeFragmentBlogAdapter.HomeFragmentBlogViewHolder>() {
    private val vehicleBlog: VehicleBlogResponse = VehicleBlogResponse()
    var onItemClickCallback: ((VehicleBlogResponseItem) -> Unit)? = null

    inner class HomeFragmentBlogViewHolder(private val binding: ItemBlogRvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindBlog(vehicleBlogItem: VehicleBlogResponseItem) {
            binding.apply {
                vehicleBlogItem.blogImage?.let { imgBackground.loadImageFromURL(it) }
                blogItemTitleTextView.text = vehicleBlogItem.blogTitle

                itemLayout.setOnClickListener {
                    onItemClickCallback?.invoke(vehicleBlogItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentBlogViewHolder {
        val binding = ItemBlogRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeFragmentBlogViewHolder(binding)
    }

    override fun getItemCount(): Int = vehicleBlog.size

    override fun onBindViewHolder(holder: HomeFragmentBlogViewHolder, position: Int) {
        holder.bindBlog(vehicleBlog[position])
    }

    fun setVehicleBlog(vehicleBlog: VehicleBlogResponse) {
        this.vehicleBlog.clear()
        this.vehicleBlog.addAll(vehicleBlog)
        notifyItemChanged(vehicleBlog.size)
    }

}