package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponseItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ItemBlogRvBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.loadImageFromURL

class HomeFragmentBlogAdapter(private val vehicleBlog: VehicleBlogResponse) :
    RecyclerView.Adapter<HomeFragmentBlogAdapter.HomeFragmentBlogViewHolder>() {
    var onItemClickCallback: ((VehicleBlogResponseItem) -> Unit)? = null

    class HomeFragmentBlogViewHolder(private val binding: ItemBlogRvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindBlog(vehicleBlogItem: VehicleBlogResponseItem, onItemClickCallback: ((VehicleBlogResponseItem) -> Unit)?) {
            binding.apply {
                vehicleBlogItem.blogImage?.let { imgBackground.loadImageFromURL(it) }
                blogItemTitleTextView.text = vehicleBlogItem.blogTitle

                root.setOnClickListener {
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
        holder.bindBlog(vehicleBlog[position], onItemClickCallback)
    }

}