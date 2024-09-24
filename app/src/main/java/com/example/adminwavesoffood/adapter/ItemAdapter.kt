package com.example.adminwavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.databinding.BaseItemBinding
import com.example.adminwavesoffood.models.ItemModel

class ItemAdapter(
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = mutableListOf<ItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        BaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(data: List<ItemModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: BaseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel) {
            binding.apply {
                itemName.text = item.name
                itemPrice.text = item.price
                itemImage.setImageResource(item.imageResId)

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
                    items.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }
}
