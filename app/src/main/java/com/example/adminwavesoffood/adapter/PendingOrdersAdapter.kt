package com.example.adminwavesoffood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.databinding.PendingOrdersItemBinding
import com.example.adminwavesoffood.models.PendingOrderModel

class PendingOrdersAdapter(private val pendingOrders: MutableList<PendingOrderModel>) :
    RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder>() {
    inner class PendingOrdersViewHolder(private var binding: PendingOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false

        fun bind(position: Int) {
            binding.apply {
                userName.text = pendingOrders[position].name
                quantityItem.text = pendingOrders[position].quantity.toString()
                itemImage.setImageResource(pendingOrders[position].imageUriId)

                btnAccept.apply {
                    if (!isAccepted) {
                        text = "Accepted"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is accepted")
                        } else {
                            pendingOrders.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is dispatched")
                        }
                    }
                }
            }
        }

        private fun showToast(s: String) {
            Toast.makeText(binding.root.context, s, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrdersViewHolder {
        val binding =
            PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrdersViewHolder(binding)
    }

    override fun getItemCount(): Int = pendingOrders.size

    override fun onBindViewHolder(holder: PendingOrdersViewHolder, position: Int) {
        holder.bind(position)
    }
}