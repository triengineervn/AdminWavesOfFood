package com.example.adminwavesoffood.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.databinding.DeliveryItemBinding
import androidx.core.content.ContextCompat


class DeliveryAdapter(
    private val deliveryName: MutableList<String>,
    private val deliveryStatus: MutableList<Boolean>
) :
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
                userName.text = deliveryName[position]
                if (deliveryStatus[position]) {
                    statusPayment.text = context.getString(R.string.invalid_received)
                    statusPayment.setTextColor(
                        colorMap[context.getString(R.string.invalid_received)] ?: R.color.overlay
                    )
                    statusPaymentImage.backgroundTintList = ColorStateList.valueOf(
                        colorMap[context.getString(R.string.invalid_received)] ?: R.color.overlay,
                    )
                } else {
                    statusPayment.text = context.getString(R.string.invalid_not_received)
                    statusPayment.setTextColor(
                        colorMap[context.getString(R.string.invalid_not_received)]
                            ?: R.color.overlay
                    )
                    statusPaymentImage.backgroundTintList = ColorStateList.valueOf(
                        colorMap[context.getString(R.string.invalid_not_received)]
                            ?: R.color.overlay,
                    )
                }

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

    override fun getItemCount(): Int = deliveryName.size
}