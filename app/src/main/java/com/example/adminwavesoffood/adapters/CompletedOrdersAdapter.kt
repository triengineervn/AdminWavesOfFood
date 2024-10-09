package com.example.adminwavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.databinding.CompletedOrdersItemBinding
import com.example.adminwavesoffood.helpers.xhelper
import com.example.adminwavesoffood.models.OrdersModel

class CompletedOrdersAdapter(private val listOfOrders: MutableList<OrdersModel>) :
    RecyclerView.Adapter<CompletedOrdersAdapter.CompletedOrdersViewHolder>() {
    class CompletedOrdersViewHolder(private val binding: CompletedOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listOfOrders: MutableList<OrdersModel>, position: Int) {
            binding.apply {
                userName.text = listOfOrders[position].userName
                totalPrice.text = listOfOrders[position].totalPrice + "$"
                timeOrder.text =
                    xhelper.formatTime(listOfOrders[position].currentTime!!)
                locationOrder.text = listOfOrders[position].address
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedOrdersViewHolder {
        val binding =
            CompletedOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompletedOrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedOrdersViewHolder, position: Int) {
        holder.bind(listOfOrders, position)
    }

    override fun getItemCount(): Int = listOfOrders.size


}
