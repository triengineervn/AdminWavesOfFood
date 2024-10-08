package com.example.adminwavesoffood.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.adapter.DeliveryAdapter
import com.example.adminwavesoffood.databinding.ActivityOrderDispatchBinding
import com.example.adminwavesoffood.models.OrdersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderDispatchActivity : AppCompatActivity() {
    private val binding: ActivityOrderDispatchBinding by lazy {
        ActivityOrderDispatchBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private var listOfCompletedOrder: MutableList<OrdersModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        retrieveCompletedOrder()



        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun retrieveCompletedOrder() {
        val completedOrderRef = database.reference.child("users/${userId}/completedOrders")
            .orderByChild("currentTime")

        completedOrderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfCompletedOrder.clear()
                for (orderSnapshot in snapshot.children) {
                    val completedOrder = orderSnapshot.getValue(OrdersModel::class.java)
                    completedOrder?.let {
                        listOfCompletedOrder.add(it)
                    }
                }
                listOfCompletedOrder.reverse()
                setData()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setData() {
        val customerName = mutableListOf<String>()
        val paymentStatus = mutableListOf<Boolean>()

        for (order in listOfCompletedOrder) {
            order.userName.let {
                customerName.add(it!!)
            }
            paymentStatus.add(order.paymentReceived)
        }

        val adapter = DeliveryAdapter(customerName, paymentStatus)
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.itemRecyclerView.adapter = adapter
    }
}

