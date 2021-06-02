package com.rumosoft.photogallery.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rumosoft.photogallery.databinding.ListItemImageBinding
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.presentation.listeners.ImageClickListener

class ImagesAdapter(
    private val clickListener: ImageClickListener
) :
    RecyclerView.Adapter<ImagesAdapter.ItemViewHolder>() {

    var data = listOf<Image>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemImageBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    class ItemViewHolder(private val binding: ListItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image, clickListener: ImageClickListener) {
            binding.image = image
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

