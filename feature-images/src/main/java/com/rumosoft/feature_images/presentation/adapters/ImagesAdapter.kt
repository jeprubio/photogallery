package com.rumosoft.feature_images.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rumosoft.feature_images.databinding.ListItemImageBinding
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.presentation.listeners.ImageClickListener

class ImagesAdapter(
        private val editClickListener: ImageClickListener,
        private val deleteClickListener: ImageClickListener,
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
        holder.bind(data[position], editClickListener, deleteClickListener)
    }

    class ItemViewHolder(private val binding: ListItemImageBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(
                image: Image,
                editClickListener: ImageClickListener,
                deleteClickListener: ImageClickListener
        ) {
            binding.image = image
            binding.editClickListener = editClickListener
            binding.deleteClickListener = deleteClickListener
            binding.executePendingBindings()
        }
    }
}

