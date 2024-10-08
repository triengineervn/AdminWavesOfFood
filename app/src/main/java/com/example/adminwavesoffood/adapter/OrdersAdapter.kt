package com.example.adminwavesoffood.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.databinding.PendingOrdersItemBinding
import com.example.adminwavesoffood.models.OrdersModel

class OrdersAdapter(
    private var pendingOrders: MutableList<OrdersModel>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<OrdersAdapter.PendingOrdersViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemDispatch(position: Int)
        fun onItemAccept(position: Int)
        fun deleteThisItemFromOrders(dispatchItemPushKey: String)
    }

    inner class PendingOrdersViewHolder(private var binding: PendingOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isDispatch = false

        fun bind(position: Int) {

            binding.apply {
                userName.text = pendingOrders[position].userName
                quantityItem.text = pendingOrders[position].totalPrice

                btnAccept.apply {
                    setOnClickListener {
                        if (!isDispatch) {
                            text = "Dispatch"
                            isDispatch = true
                            itemClickListener.onItemAccept(adapterPosition)
                        } else {
                            itemClickListener.onItemDispatch(adapterPosition)
                            pendingOrders.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                        }
                    }
                }


                itemView.setOnClickListener {
                    itemClickListener.onItemClick(adapterPosition)
                }

            }
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