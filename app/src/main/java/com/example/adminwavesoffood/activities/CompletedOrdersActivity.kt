package com.example.adminwavesoffood.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.adapters.CompletedOrdersAdapter
import com.example.adminwavesoffood.databinding.ActivityCompletedOrdersBinding
import com.example.adminwavesoffood.models.OrdersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompletedOrdersActivity : AppCompatActivity() {
    private val binding: ActivityCompletedOrdersBinding by lazy {
        ActivityCompletedOrdersBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private var listOfOrders: MutableList<OrdersModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid


        retrieveCompletedOrders()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun retrieveCompletedOrders() {
        val completedOrderRef = database.reference.child("users/${userId}/completedOrders")
        completedOrderRef.orderByChild("currentTime")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listOfOrders.clear()
                    for (order in snapshot.children) {
                        val orderData = order.getValue(OrdersModel::class.java)
                        orderData.let { listOfOrders.add(it!!) }
                    }

                    setAdapter()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CompletedOrdersActivity, error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun setAdapter() {
        val adapter = CompletedOrdersAdapter(
            listOfOrders
        )

        binding.completedOrdersRecyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.completedOrdersRecyclerView.adapter = adapter
    }
}