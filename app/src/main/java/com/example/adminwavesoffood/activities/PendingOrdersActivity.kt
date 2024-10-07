package com.example.adminwavesoffood.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.adapter.OrdersAdapter

import com.example.adminwavesoffood.databinding.ActivityPendingOrdersBinding
import com.example.adminwavesoffood.models.OrdersModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PendingOrdersActivity : AppCompatActivity() {

    private val binding: ActivityPendingOrdersBinding by lazy {
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }

    private var listOfOrders: MutableList<OrdersModel> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var ordersRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        ordersRef = database.reference.child("users")

        retrievePendingOrders()


        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun retrievePendingOrders() {
        ordersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val ordersSnapshot = orderSnapshot.child("orders")
                    for (orderItemSnapshot in ordersSnapshot.children) {
                        val orderItem = orderItemSnapshot.getValue(OrdersModel::class.java)
                        orderItem?.let { listOfOrders.add(it) }
                    }
                    setAdapter()
                }
            }

            private fun setAdapter() {

                val adapter = OrdersAdapter(listOfOrders, this@PendingOrdersActivity)
                binding.pendingOrdersRecyclerView.layoutManager =
                    LinearLayoutManager(this@PendingOrdersActivity)
                binding.pendingOrdersRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PendingOrdersActivity, "Cancelled", Toast.LENGTH_SHORT).show()
            }

        })
    }
}