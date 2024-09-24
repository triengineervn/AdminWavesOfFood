package com.example.adminwavesoffood.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.databinding.DeliveryItemBinding
import com.example.adminwavesoffood.models.DeliveryModel
import androidx.core.content.ContextCompat


class DeliveryAdapter(private val deliveryModel: MutableList<DeliveryModel>) :
    RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {
    inner class DeliveryViewHolder(private var binding: DeliveryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val context = binding.root.context
            val colorMap = mapOf(
                context.getString(R.string.invalid_received) to ContextCompat.getColor(
                    context,
                    R.color.success
                ),
                context.getString(R.string.invalid_not_received) to ContextCompat.getColor(
                    context,
                    R.color.error
                ),
                context.getString(R.string.invalid_pending) to ContextCompat.getColor(
                    context,
                    R.color.overlay
                )
            )
            binding.apply {
                userName.text = deliveryModel[position].name
                statusPayment.text = deliveryModel[position].status
                statusPayment.setTextColor(
                    colorMap[deliveryModel[position].status] ?: R.color.overlay
                )
                statusPaymentImage.backgroundTintList = ColorStateList.valueOf(
                    colorMap[deliveryModel[position].status] ?: R.color.overlay,
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryAdapter.DeliveryViewHolder {
        val binding =
            DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = deliveryModel.size
}