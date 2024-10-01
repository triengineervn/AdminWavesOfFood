package com.example.adminwavesoffood.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.databinding.BaseItemBinding
import com.example.adminwavesoffood.models.ItemModel

class ItemAdapter(
    private val context: Context,
    private val listItem: MutableList<ItemModel>,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        BaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount() = listItem.size

    inner class ItemViewHolder(private val binding: BaseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel) {
            binding.apply {
                itemName.text = item.name
                itemPrice.text = item.price
                
                // Load the image using Glide
                Glide.with(context)
                    .load(item.image) // Use the constructed full URL
                    .override(72, 72) // Resize the image to 72x72 pixels
                    .into(itemImage)

                increaseQuantityBtn.setOnClickListener {
                    item.quantity++
                    itemQuantity.text = item.quantity.toString()
                }

                decreaseQuantityBtn.setOnClickListener {
                    if (item.quantity > 1) {
                        item.quantity--
                        itemQuantity.text = item.quantity.toString()
                    }
                }

                deleteBtn.setOnClickListener {
                    listItem.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }
}
