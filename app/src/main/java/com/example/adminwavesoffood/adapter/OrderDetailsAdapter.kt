package com.example.adminwavesoffood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.databinding.OrderDetailsItemBinding

class OrderDetailsAdapter(
    private val foodName: MutableList<String>,
    private val foodQuantity: MutableList<String>,
    private val foodPrice: MutableList<String>,
    private val foodImage: MutableList<String>,
    private val context: Context
) : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {
    class OrderDetailsViewHolder(private val binding: OrderDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            foodName: MutableList<String>,
            foodImage: MutableList<String>,
            foodQuantity: MutableList<String>,
            foodPrice: MutableList<String>,
            position: Int,
            context: Context
        ) {
            binding.apply {
                foodNameTV.text = foodName[position]
                foodQuantityTV.text = foodQuantity[position]
                foodPriceTV.text = foodPrice[position]
                val uri = Uri.parse(foodImage[position])
                Glide.with(context).load(uri).into(foodImageView)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsViewHolder {
        val binding =
            OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderDetailsViewHolder,
        position: Int
    ) {
        holder.bind(foodName, foodImage, foodQuantity, foodPrice, position, context)
    }

    override fun getItemCount(): Int = foodName.size

}
