package com.example.adminwavesoffood.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.adapters.OrderDetailsAdapter
import com.example.adminwavesoffood.databinding.ActivityOrdersDetailBinding
import com.example.adminwavesoffood.models.OrdersModel

class OrdersDetailActivity : AppCompatActivity() {

    private val binding: ActivityOrdersDetailBinding by lazy {
        ActivityOrdersDetailBinding.inflate(layoutInflater)
    }

    private lateinit var userName: String
    private lateinit var userAddress: String
    private lateinit var userPhone: String
    private var foodImage: MutableList<String> = mutableListOf()
    private var foodName: MutableList<String> = mutableListOf()
    private var foodPrice: MutableList<String> = mutableListOf()
    private var foodQuantity: MutableList<String> = mutableListOf()
    private lateinit var totalPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        intent.getParcelableExtra<OrdersModel>("userOrderDetail")?.let { orderData ->
            userName = orderData.userName ?: "N/A"
            userAddress = orderData.address ?: "N/A"
            userPhone = orderData.phone ?: "N/A"
            totalPrice = orderData.totalPrice.toString()

            processCartItems(orderData)

            setOrderDetailsData()
            setAdapter()
        }
    }

    private fun processCartItems(orderData: OrdersModel) {
        orderData.cartItems?.let { cartItems ->
            foodName = cartItems.map { it.name ?: "Unknown" }.toMutableList()
            foodPrice = cartItems.map { it.price.toString() }.toMutableList()
            foodQuantity = cartItems.map { it.quantity.toString() }.toMutableList()
            foodImage = cartItems.map { it.image ?: "" }.toMutableList()
        } ?: run {
            Log.e("minhtri", orderData.toString())
        }
    }

    private fun setAdapter() {
        val orderDetailsAdapter =
            OrderDetailsAdapter(foodName, foodQuantity, foodPrice, foodImage, this)
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.orderDetailsRecyclerView.adapter = orderDetailsAdapter
    }

    private fun setOrderDetailsData() {
        binding.nameEditText.text = userName
        binding.addressEditText.text = userAddress
        binding.phoneEditText.text = userPhone
        binding.totalPriceText.text = "$totalPrice$"
    }
}
